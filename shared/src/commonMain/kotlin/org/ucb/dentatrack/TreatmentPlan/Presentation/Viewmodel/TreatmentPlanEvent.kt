package org.ucb.dentatrack.TreatmentPlan.Presentation.Viewmodel

import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStepStatus

sealed interface TreatmentPlanEvent {

    data class Start(
        val userId: String
    ) : TreatmentPlanEvent

    data class TitleChanged(
        val value: String
    ) : TreatmentPlanEvent

    data class DateChanged(
        val value: String
    ) : TreatmentPlanEvent

    data class NoteChanged(
        val value: String
    ) : TreatmentPlanEvent

    data class StatusChanged(
        val status: TreatmentStepStatus
    ) : TreatmentPlanEvent

    data class EditClicked(
        val stepId: Int
    ) : TreatmentPlanEvent

    data object NewClicked : TreatmentPlanEvent
    data object SaveClicked : TreatmentPlanEvent
    data object BackClicked : TreatmentPlanEvent
}