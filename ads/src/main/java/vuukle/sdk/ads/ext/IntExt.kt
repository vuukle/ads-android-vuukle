package vuukle.sdk.ads.ext

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt

fun Int.pxToDp(context: Context): Int {
    val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
    val dp = this / (metrics.densityDpi / 160f)
    return dp.roundToInt()
}