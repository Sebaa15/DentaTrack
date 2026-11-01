package org.ucb.dentatrack.TreatmentPlan.Presentation.Viewmodel

import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStep
import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStepStatus

data class TreatmentPlanState(
    val userId: String? = null,
    val steps: List<TreatmentStep> = emptyList(),

    val editingId: Int = 0,
    val title: String = "",
    val date: String = "",
    val note: String = "",
    val editingStatus: TreatmentStepStatus =
        TreatmentStepStatus.PENDING,

    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val message: String? = null,
    val error: String? = null
) {
    val completedCount: Int
        get() = steps.count {
            it.status == TreatmentStepStatus.COMPLETED
        }

    val progress: Float
        get() = if (steps.isEmpty()) {
            0f
        } else {
            completedCount.toFloat() / steps.size
        }
}