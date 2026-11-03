package org.ucb.dentatrack.Care.Domain.UseCase

import org.ucb.dentatrack.Care.Domain.Repository.CareRepository

class GetDailyCareUseCase(
    private val repository: CareRepository
) {
    suspend operator fun invoke(
        userId: String,
        date: String
    ) = repository.getDailyCare(userId, date)
}