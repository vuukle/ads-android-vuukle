package vuukle.sdk.ads.repository

import android.content.Context
import android.util.Log
import retrofit2.Response
import vuukle.sdk.ads.constants.LoggerConstants
import vuukle.sdk.ads.helper.DeviceHelper
import vuukle.sdk.ads.model.body.LoggingBody
import vuukle.sdk.ads.model.response.LogResponse
import vuukle.sdk.ads.repository.network.ApiClient
import vuukle.sdk.ads.repository.network.api.ApiService
import java.util.*

class VuukleLogRepository(
    val context: Context
) {

    private fun withApiClient(): ApiService? =
        ApiClient().withApiClient()

    fun sendLogs(stackTrace: List<String>, onContinue: () -> Unit) {
        try {
            Thread {
                val date = Date(System.currentTimeMillis())
                val body = LoggingBody(
                    deviceId = DeviceHelper.getDeviceId(context),
                    message = "new log from Android: $date",
                    stackTrace = stackTrace
                )
                Log.i(LoggerConstants.VUUKLE_ADS_LOG, body.toString())
                val response: Response<LogResponse>? = withApiClient()?.sendLog(body)?.execute()
                if (response != null &&
                    response.isSuccessful &&
                    response.code() == 200 &&
                    response.body() != null &&
                    response.body()?.success == true &&
                    response.body()?.code == 200
                ) {
                    android.os.Handler(context.mainLooper).run {
                        Log.i(LoggerConstants.VUUKLE_ADS_LOG, response.body().toString())
                        onContinue.invoke()
                    }
                } else {
                    // todo log

                }
            }.start()
        } catch (e: Exception) {
            // todo log
        }
    }
}