package org.ucb.dentatrack.Logs.Domain.UseCase

import org.ucb.dentatrack.Logs.Domain.Repository.LogsRepository

class SendLogsUseCase(
    private val repository: LogsRepository
) {
    suspend operator fun invoke(
        message: String,
        userId: String
    ): Boolean {
        return repository.sendLog(message, userId)
    }
}