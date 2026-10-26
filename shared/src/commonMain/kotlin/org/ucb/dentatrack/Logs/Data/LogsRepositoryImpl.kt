package org.ucb.dentatrack.Logs.Data

import org.ucb.dentatrack.Logs.Domain.Repository.LogsRepository

class LogsRepositoryImpl(
    private val remote: LogsRemoteDataSource
) : LogsRepository {

    override suspend fun sendLog(
        message: String,
        userId: String
    ): Boolean {
        return remote.sendLog(message, userId)
    }
}