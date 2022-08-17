package vuukle.sdk.ads.manager

import android.content.Context
import vuukle.sdk.ads.callback.VuukleAdsErrorCallback
import vuukle.sdk.ads.callback.VuukleAdsResultCallback
import vuukle.sdk.ads.widget.VuukleAdView

interface VuukleAds {
    fun initialize(applicationContext: Context): Result<Boolean>
    fun addErrorListener(vuukleAdsErrorCallback: VuukleAdsErrorCallback)
    fun addResultListener(vuukleAdsResultCallback: VuukleAdsResultCallback)
    fun createBanner(adView: VuukleAdView): Result<String>
    fun startAdvertisement()
    fun resume()
    fun pause()
    fun destroy()
}