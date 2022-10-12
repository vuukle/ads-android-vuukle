package vuukle.sdk.ads.manager

interface VuukleLoggerManager {

    fun writeLog(logText: String)

    fun getStackTraces(): List<String>

    fun sendLogRequest()
}