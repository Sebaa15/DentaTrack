package org.ucb.dentatrack.Care.Domain.UseCase

import org.ucb.dentatrack.Care.Domain.Repository.CareRepository

class SaveCareItemUseCase(
    private val repository: CareRepository
) {
    suspend operator fun invoke(
        userId: String,
        title: String,
        note: String
    ) {
        require(title.isNotBlank()) {
            "Ingresa una indicación"
        }

        require(title.length <= 120) {
            "La indicación es demasiado larga"
        }

        require(note.length <= 500) {
            "La nota es demasiado larga"
        }

        repository.saveCareItem(
            userId,
            title.trim(),
            note.trim()
        )
    }
}