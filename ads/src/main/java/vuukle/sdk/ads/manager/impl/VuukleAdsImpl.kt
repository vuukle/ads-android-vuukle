package vuukle.sdk.ads.manager.impl

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import org.prebid.mobile.AdUnit
import org.prebid.mobile.ResultCode
import vuukle.sdk.ads.callback.VuukleAdsErrorCallback
import vuukle.sdk.ads.callback.VuukleAdsResultCallback
import vuukle.sdk.ads.exception.VuukleAdLoadFail
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

    override fun initialize(
        applicationContext: Context
    ): Result<Boolean> {
        doInitialize(applicationContext)
        configureAds(applicationContext)
        return Result.success(true)
    }

    private fun configureAds(applicationContext: Context) {
        val configuration = RequestConfiguration.Builder().build()
        MobileAds.setRequestConfiguration(configuration)
        // Initialize the Mobile Ads SDK with an AdMob App ID.
        MobileAds.initialize(applicationContext) {
            // TODO logging
        }
        prebidWrapper.initializeHost(applicationContext)
    }

    private fun doInitialize(applicationContext: Context) {
        provider = VuukleAdsProvider(applicationContext)
        prebidWrapper = PrebidWrapper()
    }

    override fun startAdvertisement() {
        ads.forEach {
            val adView = it.value.adView
            val adUnit = it.value.adUnit
            loadAd(it.key, adView, adUnit)
        }
    }

    private fun loadAd(id: String, adView: AdView, adUnit: AdUnit) {
        val builder = AdManagerAdRequest
            .Builder()
        adUnit.fetchDemand(builder) {
            if (it != ResultCode.SUCCESS) {
                Log.i("testing--->>>", "fetchDemand error ${it.name}")
                if (this::vuukleAdsErrorCallback.isInitialized) {
                    vuukleAdsErrorCallback.onError(VuukleAdLoadFail(it.toString()))
                }
            } else {
                val request = builder.build()
                adView.loadAd(request)
                Log.i("testing--->>>", "fetchDemand success")
                if (this::vuukleAdsResultCallback.isInitialized) {
                    vuukleAdsResultCallback.onDemandFetched(id)
                }
            }
        }
    }

    override fun resume() {
        ads.forEach {
            it.value.adView.resume()
        }
    }

    override fun pause() {
        ads.forEach {
            it.value.adView.pause()
        }
    }

    override fun destroy() {
        ads.forEach {
            it.value.adView.destroy()
        }
    }

    override fun addErrorListener(vuukleAdsErrorCallback: VuukleAdsErrorCallback) {
        this.vuukleAdsErrorCallback = vuukleAdsErrorCallback
    }

    override fun addResultListener(vuukleAdsResultCallback: VuukleAdsResultCallback) {
        this.vuukleAdsResultCallback = vuukleAdsResultCallback
    }

    override fun createBanner(adView: VuukleAdView): Result<String> {
        val adId = UUID.randomUUID().toString()
        val bannerAdUnit = prebidWrapper.createBannerAdUnit(
            provider.getAdsConfigurationId(),
            adView.getVuukleAdSize().width(),
            adView.getVuukleAdSize().height()
        )
        ads[adId] = AdItem(adView.getAdView() as AdView, bannerAdUnit)
        Log.i("testing--->>>", "Banner is created: $adId")
        return Result.success(adId)
    }
}