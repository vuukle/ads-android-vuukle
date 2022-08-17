package com.vuukle.ads

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vuukle.sdk.ads.callback.VuukleAdsErrorCallback
import vuukle.sdk.ads.callback.VuukleAdsResultCallback
import vuukle.sdk.ads.exception.VuukleAdsException
import vuukle.sdk.ads.manager.VuukleAds
import vuukle.sdk.ads.manager.impl.VuukleAdsImpl
import vuukle.sdk.ads.model.VuukleAdSize
import vuukle.sdk.ads.widget.VuukleAdView

class HomeActivity : AppCompatActivity() {

    private val vuukleAds: VuukleAds = VuukleAdsImpl()

    private val vuukleAdViewTop: VuukleAdView by lazy { findViewById(R.id.vuukle_ad_view_top) }
    private val vuukleAdViewBottom: VuukleAdView by lazy { findViewById(R.id.vuukle_ad_view_bottom) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAds()
    }

    private fun initAds() {
        // Initialize Vuukle Ads
        vuukleAds.initialize(this)
        // Create Top banners
        vuukleAdViewTop.setAdSize(VuukleAdSize.Type.BANNER)
        vuukleAdViewTop.setAdUnitId(AdsConstants.HomePage.BANNER_1_AD_UNIT_ID)
        vuukleAds.createBanner(vuukleAdViewTop)
        // Create Bottom banners
        vuukleAdViewBottom.setAdSize(VuukleAdSize.Type.BANNER)
        vuukleAdViewBottom.setAdUnitId(AdsConstants.HomePage.BANNER_2_AD_UNIT_ID)
        vuukleAds.createBanner(vuukleAdViewBottom)
        // Observing Ads results
        vuukleAds.addResultListener(object : VuukleAdsResultCallback {
            override fun onDemandFetched(id: String) {
                Toast.makeText(this@HomeActivity, id, Toast.LENGTH_SHORT).show()
            }
        })
        // Handling errors
        vuukleAds.addErrorListener(object : VuukleAdsErrorCallback {
            override fun onError(error: VuukleAdsException) {

            }
        })
        // start advertisement
        vuukleAds.startAdvertisement()
    }

    override fun onResume() {
        super.onResume()
        vuukleAds.resume()
    }

    override fun onPause() {
        super.onPause()
        vuukleAds.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        vuukleAds.destroy()
    }
}