package org.ucb.dentatrack.TreatmentPlan.Domain.Model

data class TreatmentStep(
    val id: Int = 0,
    val title: String,
    val date: String = "",
    val note: String = "",
    val status: TreatmentStepStatus =
        TreatmentStepStatus.PENDING
)

enum class TreatmentStepStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}