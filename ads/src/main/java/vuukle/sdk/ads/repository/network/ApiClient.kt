package vuukle.sdk.ads.repository.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vuukle.sdk.ads.repository.network.api.ApiService
import java.util.concurrent.TimeUnit

open class ApiClient {

    companion object {
        const val BASE_URL = "http://publish.vuukle.com/api/"
        const val HEADER_KEY = "token"
        const val HEADER_VALUE = "cec65743-a9b4-458d-9da6-086657fe6ccf"
    }

    fun withApiClient(): ApiService? =
        Thread().run {
            try {
                val okHttpClient = httpClientNonAuth().build()
                val retrofitBuilder = Retrofit.Builder().apply {
                    baseUrl(BASE_URL)
                    client(okHttpClient)
                    addConverterFactory(GsonConverterFactory.create())
                }
                val retrofit = retrofitBuilder.build()
                return@run retrofit.create(ApiService::class.java)
            } catch (error: Exception) {
                return@run null
            }
        }

    private fun httpClientNonAuth(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .addHeader(HEADER_KEY, HEADER_VALUE)
                    .build()
                chain.proceed(request)
            })
            .addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            })
    }
}