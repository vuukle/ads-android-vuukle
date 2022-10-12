package vuukle.sdk.ads.model.response


import com.google.gson.annotations.SerializedName

data class LogResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("messages")
    val messages: List<String?>?,
    @SerializedName("success")
    val success: Boolean?
)