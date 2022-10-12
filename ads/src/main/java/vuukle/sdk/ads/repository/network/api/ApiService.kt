package vuukle.sdk.ads.repository.network.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import vuukle.sdk.ads.model.body.LoggingBody
import vuukle.sdk.ads.model.response.LogResponse
import vuukle.sdk.ads.repository.network.ApiClient

interface ApiService {

    @POST("ExceptionLogging/Log/${ApiClient.HEADER_VALUE}")
    fun sendLog(
        @Body body: LoggingBody
    ): Call<LogResponse>
}