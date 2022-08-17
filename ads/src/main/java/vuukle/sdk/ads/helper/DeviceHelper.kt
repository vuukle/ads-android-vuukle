package vuukle.sdk.ads.helper

import android.content.Context

object DeviceHelper {

    fun getPackageName(context: Context): String {
        return context.packageName
    }
}