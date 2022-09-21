package com.vuukle.ads.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vuukle.ads.R
import com.vuukle.ads.constants.AdsConstants
import com.vuukle.ads.view.fragment.VuukleAdsFirstFragment
import com.vuukle.ads.view.fragment.VuukleAdsSecondFragment
import vuukle.sdk.ads.callback.VuukleAdsErrorCallback
import vuukle.sdk.ads.callback.VuukleAdsResultCallback
import vuukle.sdk.ads.constants.LoggerConstants
import vuukle.sdk.ads.exception.VuukleAdsException
import vuukle.sdk.ads.manager.VuukleAds
import vuukle.sdk.ads.manager.impl.VuukleAdsImpl
import vuukle.sdk.ads.model.VuukleAdSize
import vuukle.sdk.ads.widget.VuukleAdView


class VuukleAdsActivity : AppCompatActivity() {

    private val previous: Button by lazy { findViewById(R.id.previous) }
    private val next: Button by lazy { findViewById(R.id.next) }

    private val vuukleAds: VuukleAds = VuukleAdsImpl()

    private val fragments = arrayListOf(
        VuukleAdsFirstFragment(),
        VuukleAdsSecondFragment(),
    )

    private var lastIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vuukle_ads)
        navigateFragments(VuukleAdsFirstFragment())
        initOnClicks()
    }

    fun initAds(vuukleAdView: VuukleAdView) {
        // Initialize Vuukle Ads
        vuukleAds.initialize(this)
        // Create Top banners
        vuukleAdView.setAdSize(VuukleAdSize.Type.BANNER)
        vuukleAdView.setAdUnitId(AdsConstants.HomePage.BANNER_AD_UNIT_ID_1)
        vuukleAds.createBanner(vuukleAdView)
        // Observing Ads results
        vuukleAds.addResultListener(object : VuukleAdsResultCallback {
            override fun onDemandFetched(id: String) {
                Toast.makeText(this@VuukleAdsActivity, id, Toast.LENGTH_SHORT).show()
            }
        })
        // Handling errors
        vuukleAds.addErrorListener(object : VuukleAdsErrorCallback {
            override fun onError(error: VuukleAdsException) {
                Log.i(LoggerConstants.VUUKLE_ADS_LOG, error.toString())
            }
        })
        // start advertisement
        vuukleAds.startAdvertisement()
    }

    private fun initOnClicks() {
        previous.setOnClickListener {
            if (lastIndex == 0) {
                return@setOnClickListener
            }
            lastIndex -= 1
            navigateFragments(fragments[lastIndex])
        }
        next.setOnClickListener {
            if (lastIndex == fragments.size - 1) {
                return@setOnClickListener
            }
            lastIndex += 1
            navigateFragments(fragments[lastIndex])
        }
    }

    private fun navigateFragments(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}