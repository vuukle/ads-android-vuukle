package vuukle.sdk.ads.ext

import android.util.Log
import vuukle.sdk.ads.constants.LoggerConstants
import vuukle.sdk.ads.manager.impl.VuukleLogger
import java.util.*

fun Any.vuukleLog(tag: String, value: String? = null) {
    try {
        val currentThreadName = Thread.currentThread().name
        val logMessage = "$currentThreadName ".plus(
            "${Date(System.currentTimeMillis())} "
        ).plus(
            "$tag "
        ).plus(
            "$value "
        ).plus(
            ":${UUID.randomUUID()} "
        )
        VuukleLogger.getInstance()?.writeLog(logMessage)
        Log.i(LoggerConstants.VUUKLE_ADS_LOG, logMessage)
    } catch (error: Exception) {
        error.printStackTrace()
    }
}

fun Any.vuukleLog(tag: String, value: String, throwable: Throwable) {
    vuukleLog(tag, value.plus(" :${throwable.stackTraceToString()}"))
    throwable.printStackTrace()
}

fun Any.vuukleLog(tag: String, throwable: Throwable) {
    vuukleLog(tag, throwable.stackTraceToString())
    throwable.printStackTrace()
}