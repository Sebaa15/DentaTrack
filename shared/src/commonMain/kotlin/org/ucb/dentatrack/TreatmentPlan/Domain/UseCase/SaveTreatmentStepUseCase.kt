package org.ucb.dentatrack.TreatmentPlan.Domain.UseCase

import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStep
import org.ucb.dentatrack.TreatmentPlan.Domain.Repository.TreatmentPlanRepository

class SaveTreatmentStepUseCase(
    private val repository: TreatmentPlanRepository
) {
    suspend operator fun invoke(
        userId: String,
        step: TreatmentStep
    ) {
        require(step.title.isNotBlank()) {
            "Ingrese el nombre de la etapa"
        }

        repository.saveStep(
            userId = userId,
            step = step.copy(
                title = step.title.trim(),
                date = step.date.trim(),
                note = step.note.trim()
            )
        )
    }
}