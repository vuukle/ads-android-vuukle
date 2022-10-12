package vuukle.sdk.ads.model.body


import com.google.gson.annotations.SerializedName
import vuukle.sdk.ads.helper.DeviceHelper

data class LoggingBody(
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("model")
    val model: String = DeviceHelper.getDeviceModel(),
    @SerializedName("os")
    val os: String = "android",
    @SerializedName("osVersion")
    val osVersion: String = DeviceHelper.getDeviceOsVersion(),
    @SerializedName("stackTrace")
    val stackTrace: List<String>
)