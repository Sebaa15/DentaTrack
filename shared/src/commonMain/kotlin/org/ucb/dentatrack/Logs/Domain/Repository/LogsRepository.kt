package org.ucb.dentatrack.Logs.Domain.Repository

interface LogsRepository {
    suspend fun sendLog(
        message: String,
        userId: String
    ): Boolean
}