package com.vuukle.ads.view.activity

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vuukle.ads.R
import com.vuukle.ads.constants.AdsConstants
import vuukle.sdk.ads.callback.VuukleAdsErrorCallback
import vuukle.sdk.ads.callback.VuukleAdsResultCallback
import vuukle.sdk.ads.exception.VuukleAdsException
import vuukle.sdk.ads.manager.VuukleAds
import vuukle.sdk.ads.manager.impl.VuukleAdsImpl
import vuukle.sdk.ads.model.VuukleAdSize
import vuukle.sdk.ads.widget.VuukleAdView
import kotlin.math.roundToInt

class FluidActivity : AppCompatActivity() {

    private val vuukleAds: VuukleAds = VuukleAdsImpl()

    private val vuukleAdViewTop: VuukleAdView by lazy { findViewById(R.id.vuukle_ad_view_top) }
    private val vuukleAdViewBottom: VuukleAdView by lazy { findViewById(R.id.vuukle_ad_view_bottom) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fluid)
        initAds()
    }

    private fun initAds() {
        // Create Top banners
        vuukleAdViewTop.post {
            vuukleAdViewTop.setAdSize(
                VuukleAdSize.Type.FLUID,
                pxToDp(this, vuukleAdViewTop.width),
                pxToDp(this, vuukleAdViewTop.height)
            )
        }
        // Create Bottom banners
        vuukleAdViewBottom.post {
            vuukleAdViewBottom.setAdSize(
                VuukleAdSize.Type.FLUID,
                pxToDp(this, vuukleAdViewBottom.width),
                pxToDp(this, vuukleAdViewBottom.height)
            )
            loadContent()
        }
        // Observing Ads results
        vuukleAds.addResultListener(object : VuukleAdsResultCallback {
            override fun onDemandFetched(id: String) {
                Toast.makeText(this@FluidActivity, id, Toast.LENGTH_SHORT).show()
            }
        })
        // Handling errors
        vuukleAds.addErrorListener(object : VuukleAdsErrorCallback {
            override fun onError(error: VuukleAdsException) {
                Log.i("ewfwefwe--->>", error.toString())
            }
        })
    }

    private fun loadContent() {
        // Initialize Vuukle Ads
        vuukleAds.initialize(this)

        vuukleAdViewTop.setAdUnitId(AdsConstants.HomePage.FLUID_AD_UNIT_ID_1)
        vuukleAds.createBanner(vuukleAdViewTop)

        vuukleAdViewBottom.setAdUnitId(AdsConstants.HomePage.FLUID_AD_UNIT_ID_2)
        vuukleAds.createBanner(vuukleAdViewBottom)

        // start advertisement
        vuukleAds.startAdvertisement()
    }

    fun pxToDp(context: Context, px: Int): Int {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        val dp = px / (metrics.densityDpi / 160f)
        return dp.roundToInt()
    }
}