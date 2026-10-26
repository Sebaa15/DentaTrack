package org.ucb.dentatrack.Logs.Data

import com.develoop.logs.ELogLevel
import com.develoop.logs.ELogResultCode
import com.develoop.logs.LogData
import com.develoop.logs.LogRequest
import com.develoop.logs.LogServiceClient

class LogsRemoteDataSource(
    private val service: LogServiceClient
) {
    suspend fun sendLog(
        message: String,
        userId: String
    ): Boolean {

        val log = LogData(
            logLevel = ELogLevel.LEVEL_INFO,
            message = message,
            userId = userId,
            versionCode = 1,
            mobileTimeStamp = System.currentTimeMillis()
        )

        val request = LogRequest(
            logs = listOf(log)
        )

        val response = service.send().execute(request)

        return response.resultCode ==
                ELogResultCode.LOG_RESULT_OK
    }
}