package vuukle.sdk.ads.ext

import com.google.android.gms.ads.AdSize
import vuukle.sdk.ads.model.VuukleAdSize

fun VuukleAdSize.Type.toAdSize(): AdSize {
    return when (this) {
        VuukleAdSize.Type.BANNER -> AdSize.BANNER
        VuukleAdSize.Type.INVALID -> AdSize.INVALID
    }
}