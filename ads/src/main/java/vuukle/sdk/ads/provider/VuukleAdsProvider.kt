package vuukle.sdk.ads.provider

import android.content.Context
import vuukle.sdk.ads.constants.SdkConstants
import vuukle.sdk.ads.helper.DeviceHelper

class VuukleAdsProvider(private val context: Context) {

    private val deviceHelper = DeviceHelper

    fun getAdsConfigurationId(): String {
        return DeviceHelper.getPackageName(context)
    }

    fun getAdsHostUrl(): String {
        return SdkConstants.HOST_URL
    }
}