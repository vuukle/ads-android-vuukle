package vuukle.sdk.ads.callback

import vuukle.sdk.ads.exception.VuukleAdsException

interface VuukleAdsErrorCallback {
    fun onError(error: VuukleAdsException)
}