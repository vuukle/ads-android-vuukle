package vuukle.sdk.ads.manager.impl

import android.content.Context
import android.os.Build
import vuukle.sdk.ads.manager.VuukleLoggerManager
import java.io.*

object VuukleLogger {
    private lateinit var vuukleLoggerManager: VuukleLoggerManager
    fun init(context: Context) {
        vuukleLoggerManager = VuukleLoggerManagerImpl(context)
    }

    fun getInstance(): VuukleLoggerManager? {
        if (this::vuukleLoggerManager.isInitialized.not()) return null
        return vuukleLoggerManager
    }
}

class VuukleLoggerManagerImpl(context: Context) : VuukleLoggerManager {

    private val LOGGER_FILE_NAME = "vuukle_ads_logger.txt"

    private var fileOutputStream: FileOutputStream
    private var fileInputStream: FileInputStream
    private var inputStreamReader: InputStreamReader
    private var bufferedReader: BufferedReader

    init {
        fileOutputStream = context.openFileOutput(LOGGER_FILE_NAME, Context.MODE_PRIVATE)
        fileInputStream = context.openFileInput(LOGGER_FILE_NAME)
        inputStreamReader = InputStreamReader(fileInputStream)
        bufferedReader = BufferedReader(inputStreamReader)
    }

    override fun writeLog(logText: String) {
        try {
            fileOutputStream.write(logText.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun readLog() {
        val logsByLine = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bufferedReader.lines().toArray()
        } else {
            emptyArray()
        }

    }

}