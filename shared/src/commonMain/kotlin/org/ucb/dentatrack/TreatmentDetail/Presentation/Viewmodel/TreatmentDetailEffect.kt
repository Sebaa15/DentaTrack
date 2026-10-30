package org.ucb.dentatrack.TreatmentDetail.Presentation.Viewmodel

sealed interface TreatmentDetailEffect {

    data object NavigateBack : TreatmentDetailEffect
}