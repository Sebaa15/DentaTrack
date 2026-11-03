package org.ucb.dentatrack.Care.Domain.UseCase

import org.ucb.dentatrack.Care.Domain.Repository.CareRepository

class SetCareCompletedUseCase(
    private val repository: CareRepository
) {
    suspend operator fun invoke(
        userId: String,
        careId: Int,
        date: String,
        completed: Boolean
    ) {
        require(careId > 0)
        require(
            Regex("""\d{4}-\d{2}-\d{2}""").matches(date)
        )

        repository.setCompleted(
            userId,
            careId,
            date,
            completed
        )
    }
}