package vuukle.sdk.ads.manager.impl

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import org.prebid.mobile.AdUnit
import org.prebid.mobile.ResultCode
import vuukle.sdk.ads.callback.VuukleAdsErrorCallback
import vuukle.sdk.ads.callback.VuukleAdsResultCallback
import vuukle.sdk.ads.exception.VuukleAdLoadFail
import vuukle.sdk.ads.exception.VuukleAdsException
import vuukle.sdk.ads.exception.VuukleAdsInitializationException
import vuukle.sdk.ads.exception.VuukleLoadAdError
import vuukle.sdk.ads.manager.VuukleAds
import vuukle.sdk.ads.model.AdItem
import vuukle.sdk.ads.prebid.PrebidWrapper
import vuukle.sdk.ads.provider.VuukleAdsProvider
import vuukle.sdk.ads.widget.VuukleAdView
import java.util.*

class VuukleAdsImpl : VuukleAds {

    private lateinit var provider: VuukleAdsProvider
    private lateinit var prebidWrapper: PrebidWrapper

    // Ads structure
    private val ads = TreeMap<String, AdItem>()

    // Callbacks
    private lateinit var vuukleAdsErrorCallback: VuukleAdsErrorCallback
    private lateinit var vuukleAdsResultCallback: VuukleAdsResultCallback

    // observer
    private val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            handleResume()
            super.onResume(owner)
            Log.i("vuukle_ads_log", "MEMORY LEAK STEP ... ${ads.size}")
        }

        override fun onPause(owner: LifecycleOwner) {
            handlePause()
            super.onPause(owner)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            handleDestroy()
            super.onDestroy(owner)
        }
    }


    // Ad listener
    private var adListener: AdListener? = object : AdListener() {
        override fun onAdFailedToLoad(loadError: LoadAdError) {
            super.onAdFailedToLoad(loadError)
            val errorString = loadError.toString()
            notifyError(VuukleLoadAdError(errorString))
        }
    }

    private fun notifyError(vuukleAdsException: VuukleAdsException) {
        if (this::vuukleAdsErrorCallback.isInitialized) {
            vuukleAdsErrorCallback.onError(vuukleAdsException)
        }
    }

    override fun initialize(
        applicationContext: Context
    ): Result<Boolean> {
        if (applicationContext !is AppCompatActivity) {
            throw VuukleAdsInitializationException("We are supporting only Activity for now. Please call `initialize()` function from Activity.")
        }
        doInitialize(applicationContext)
        configureAds(applicationContext)
        (applicationContext as LifecycleOwner).lifecycle.addObserver(lifecycleObserver)
        return Result.success(true)
    }

    /**
     *  todo
     */
    private fun configureAds(activityContext: Context) {
         val configuration = RequestConfiguration.Builder().build()
        //val configuration = RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("B8A6F850FDA9B5DEB30F01B2F07971EA")).build()

        MobileAds.setRequestConfiguration(configuration)
        // Initialize the Mobile Ads SDK with an AdMob App ID.
        MobileAds.initialize(activityContext) {
            // TODO logging
        }
        prebidWrapper.initializeHost(activityContext)
    }

    /**
     *  todo
     */
    private fun doInitialize(activityContext: Context) {
        provider = VuukleAdsProvider(activityContext)
        prebidWrapper = PrebidWrapper()
    }

    /**
     *  todo
     */
    override fun startAdvertisement() {
        ads.forEach {
            val adView = it.value.adView
            val adUnit = it.value.adUnit
            loadAd(it.key, adView, adUnit)
        }
    }

    /**
     *  todo
     */
    private fun loadAd(id: String, adView: AdView, adUnit: AdUnit) {
        val builder = AdManagerAdRequest.Builder()
        adUnit.fetchDemand(builder) {
            if (it != ResultCode.SUCCESS) {
                Log.i("vuukle_ads_log", "fetchDemand error ${it.name}")
                notifyError(VuukleAdLoadFail(it.toString()))
            } else {
                val request = builder.build()
                // set adListener
                adListener?.let { listener -> adView.adListener = listener }
                adView.loadAd(request)
                Log.i("vuukle_ads_log", "fetchDemand success")
                if (this::vuukleAdsResultCallback.isInitialized) {
                    vuukleAdsResultCallback.onDemandFetched(id)
                }
            }
        }
    }

    /**
     *  todo
     */
    private fun handleResume() {
        try {
            ads.forEach {
                it.value.adView.resume()
                it.value.adUnit.resumeAutoRefresh()
                Log.i("vuukle_ads_log", "onResume")
            }
        } catch (e: Throwable) {
            // TODO
            e.printStackTrace()
        }
    }

    /**
     *  todo
     */
    private fun handlePause() {
        try {
            ads.forEach {
                it.value.adView.pause()
                it.value.adUnit.stopAutoRefresh()
                Log.i("vuukle_ads_log", "onPause")
            }
        } catch (e: Throwable) {
            //TODO
            e.printStackTrace()
        }
    }

    /**
     *  todo
     */
    private fun handleDestroy() {
        try {
            ads.forEach {
                it.value.adView.destroy()
                it.value.adUnit.stopAutoRefresh()
                Log.i("vuukle_ads_log", "destroy")
            }
        } catch (e: Throwable) {
            //TODO
            e.printStackTrace()
        } finally {
            adListener = null
        }
    }

    /**
     *  todo
     */
    override fun addErrorListener(vuukleAdsErrorCallback: VuukleAdsErrorCallback) {
        this.vuukleAdsErrorCallback = vuukleAdsErrorCallback
    }

    /**
     *  todo
     */
    override fun addResultListener(vuukleAdsResultCallback: VuukleAdsResultCallback) {
        this.vuukleAdsResultCallback = vuukleAdsResultCallback
    }

    /**
     *  todo
     */
    override fun createBanner(adView: VuukleAdView): Result<String> {
        val adId = UUID.randomUUID().toString()
        val bannerAdUnit = prebidWrapper.createBannerAdUnit(
            provider.getAdsConfigurationId(),
            adView.getVuukleAdSize().width(),
            adView.getVuukleAdSize().height()
        )
        ads[adId] = AdItem(adView.getAdView() as AdView, bannerAdUnit)
        Log.i("vuukle_ads_log", "Banner is created: $adId")
        return Result.success(adId)
    }
}