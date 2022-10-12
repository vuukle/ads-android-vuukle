package vuukle.sdk.ads.helper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings

object DeviceHelper {

    fun getPackageName(context: Context): String {
        return context.packageName
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver, Settings.Secure.ANDROID_ID
        )
    }

    fun getDeviceModel(): String {
        return Build.MODEL
    }

    fun getDeviceOsVersion(): String {
        return Build.VERSION.RELEASE
    }
}