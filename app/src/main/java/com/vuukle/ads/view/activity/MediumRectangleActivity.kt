package com.vuukle.ads.view.activity

import android.os.Bundle
import android.os.PersistableBundle
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

class MediumRectangleActivity : AppCompatActivity() {

    private val vuukleAds: VuukleAds = VuukleAdsImpl()

    private val vuukleAdViewTop: VuukleAdView by lazy { findViewById(R.id.vuukle_ad_view_top) }
    private val vuukleAdViewBottom: VuukleAdView by lazy { findViewById(R.id.vuukle_ad_view_bottom) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_rectangle)
        initAds()
    }

    private fun initAds() {
        // Initialize Vuukle Ads
        vuukleAds.initialize(this)
        // Create Top banners
        vuukleAdViewTop.setAdSize(VuukleAdSize.Type.MEDIUM_RECTANGLE)
        vuukleAdViewTop.setAdUnitId(AdsConstants.HomePage.MEDIUM_RECTANGLE_AD_UNIT_ID_1)
        vuukleAds.createBanner(vuukleAdViewTop)
        // Create Bottom banners
        vuukleAdViewBottom.setAdSize(VuukleAdSize.Type.MEDIUM_RECTANGLE)
        vuukleAdViewBottom.setAdUnitId(AdsConstants.HomePage.MEDIUM_RECTANGLE_AD_UNIT_ID_2)
        vuukleAds.createBanner(vuukleAdViewBottom)
        // Observing Ads results
        vuukleAds.addResultListener(object : VuukleAdsResultCallback {
            override fun onDemandFetched(id: String) {
                Toast.makeText(this@MediumRectangleActivity, id, Toast.LENGTH_SHORT).show()
            }
        })
        // Handling errors
        vuukleAds.addErrorListener(object : VuukleAdsErrorCallback {
            override fun onError(error: VuukleAdsException) {
                Log.i("ewfwefwe--->>", error.toString())

            }
        })
        // start advertisement
        vuukleAds.startAdvertisement()
    }
}