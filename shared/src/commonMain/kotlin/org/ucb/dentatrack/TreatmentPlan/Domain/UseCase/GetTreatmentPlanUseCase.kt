package org.ucb.dentatrack.TreatmentPlan.Domain.UseCase

import org.ucb.dentatrack.TreatmentPlan.Domain.Repository.TreatmentPlanRepository

class GetTreatmentPlanUseCase(
    private val repository: TreatmentPlanRepository
) {
    suspend operator fun invoke(userId: String) =
        repository.getSteps(userId)
}