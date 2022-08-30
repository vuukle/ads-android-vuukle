package vuukle.sdk.ads.manager

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import vuukle.sdk.ads.callback.VuukleAdsErrorCallback
import vuukle.sdk.ads.callback.VuukleAdsResultCallback
import vuukle.sdk.ads.widget.VuukleAdView

interface VuukleAds {
    fun initialize(applicationContext: Context): Boolean
    fun addErrorListener(vuukleAdsErrorCallback: VuukleAdsErrorCallback)
    fun addResultListener(vuukleAdsResultCallback: VuukleAdsResultCallback)
    fun createBanner(adView: VuukleAdView): String
    fun startAdvertisement()
}