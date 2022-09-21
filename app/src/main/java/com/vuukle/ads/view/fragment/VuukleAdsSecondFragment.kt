package com.vuukle.ads.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vuukle.ads.R
import com.vuukle.ads.view.activity.VuukleAdsActivity
import vuukle.sdk.ads.widget.VuukleAdView

class VuukleAdsSecondFragment : Fragment(R.layout.fragment_vuukle_ads_second) {

    private lateinit var vuukleAdView: VuukleAdView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vuukleAdView = requireView().findViewById(R.id.vuukle_ad_view_bottom)
        (requireActivity() as VuukleAdsActivity).initAds(vuukleAdView)
    }
}