package vuukle.sdk.ads.manager.impl

import android.content.Context
import vuukle.sdk.ads.ext.getIndexByLogId
import vuukle.sdk.ads.ext.toLogIds
import vuukle.sdk.ads.manager.VuukleLoggerManager
import vuukle.sdk.ads.repository.VuukleLogRepository
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

class VuukleLoggerManagerImpl(val context: Context) : VuukleLoggerManager {

    private val LOGGER_FILE_NAME = "vuukle_ads_logger.txt"

    private var fileOutputStream: FileOutputStream
    private var fileInputStream: FileInputStream
    private var inputStreamReader: InputStreamReader
    private var bufferedReader: BufferedReader

    init {
        fileOutputStream = context.openFileOutput(LOGGER_FILE_NAME, Context.MODE_APPEND)
        fileInputStream = context.openFileInput(LOGGER_FILE_NAME)
        inputStreamReader = InputStreamReader(fileInputStream)
        bufferedReader = BufferedReader(inputStreamReader)
    }

    override fun writeLog(logText: String) {
        try {
            val stackTraces = getStackTraces()
            if (stackTraces.isNotEmpty()) {
                fileOutputStream.write("\n$logText".toByteArray())
            } else {
                fileOutputStream.write(logText.toByteArray())
            }
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

    override fun getStackTraces(): List<String> {
        val stackTrace = arrayListOf<String>()
        var text: String?
        while (run {
                text = bufferedReader.readLine()
                text
            } != null) {
            text?.let { stackTrace.add(it) }
        }
        return stackTrace
    }

    override fun sendLogRequest() {
        val stackTrace = getStackTraces()
        if (stackTrace.isEmpty()) return
        val repository = VuukleLogRepository(context)
        repository.sendLogs(stackTrace) {
            deleteSentLogs(stackTrace)
        }
    }

    private fun deleteSentLogs(sentStackTrace: List<String>) {
        val logs = ArrayList(getStackTraces())
        val sentLogIds = sentStackTrace.toLogIds()
        sentLogIds.forEach { id ->
            logs.getIndexByLogId(id)?.let { index ->
                logs.removeAt(index)
            }
        }
        try {
            // delete content
            deleteContentOfLogFile()
            // write new logs if not empty
            logs.forEachIndexed { index, logMessage ->
                if (index == 0) {
                    fileOutputStream.write(logMessage.toByteArray())
                } else {
                    fileOutputStream.write("\n$logMessage".toByteArray())
                }
            }
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

    private fun deleteContentOfLogFile() {
        val path: String = context.filesDir.absolutePath.plus("/$LOGGER_FILE_NAME")
        val writer = PrintWriter(path)
        writer.print("")
        writer.close()
    }
}