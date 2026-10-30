package org.ucb.dentatrack.TreatmentDetail.Presentation.Viewmodel

sealed interface TreatmentDetailEvent {

    data class Start(
        val userId: String,
        val toothNumber: Int
    ) : TreatmentDetailEvent

    data object BackClicked : TreatmentDetailEvent
}