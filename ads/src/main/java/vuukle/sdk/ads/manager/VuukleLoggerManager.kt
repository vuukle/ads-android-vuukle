package vuukle.sdk.ads.manager

import android.content.Context

interface VuukleLoggerManager {

    fun writeLog(logText: String)

    fun readLog()
}