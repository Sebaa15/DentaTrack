package org.ucb.dentatrack.TreatmentPlan.Domain.Repository

import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStep

interface TreatmentPlanRepository {

    suspend fun getSteps(
        userId: String
    ): List<TreatmentStep>

    suspend fun saveStep(
        userId: String,
        step: TreatmentStep
    )
}