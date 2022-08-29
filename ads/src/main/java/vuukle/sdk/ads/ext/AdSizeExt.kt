package vuukle.sdk.ads.ext

import com.google.android.gms.ads.AdSize
import vuukle.sdk.ads.model.VuukleAdSize

fun VuukleAdSize.Type.toAdSize(): AdSize {
    return when (this) {
        VuukleAdSize.Type.BANNER -> AdSize.BANNER
        VuukleAdSize.Type.INVALID -> AdSize.INVALID
        VuukleAdSize.Type.MEDIUM_RECTANGLE -> AdSize.MEDIUM_RECTANGLE
        VuukleAdSize.Type.LARGE_BANNER -> AdSize.LARGE_BANNER
        VuukleAdSize.Type.FULL_BANNER -> AdSize.FULL_BANNER
        VuukleAdSize.Type.FLUID -> AdSize.FLUID
    }
}