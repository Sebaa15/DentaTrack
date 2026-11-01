package org.ucb.dentatrack.TreatmentPlan.Presentation.Viewmodel

sealed interface TreatmentPlanEffect {
    data object NavigateBack : TreatmentPlanEffect
}