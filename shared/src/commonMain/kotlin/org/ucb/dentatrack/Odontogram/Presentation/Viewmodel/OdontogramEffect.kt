package org.ucb.dentatrack.Odontogram.Presentation.Viewmodel

sealed interface OdontogramEffect{
    data class NavigateToTreatmentDetail(val toothNumber:Int)
        : OdontogramEffect
}