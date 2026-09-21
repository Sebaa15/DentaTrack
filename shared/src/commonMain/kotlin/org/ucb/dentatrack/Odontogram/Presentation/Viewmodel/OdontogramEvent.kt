package org.ucb.dentatrack.Odontogram.Presentation.Viewmodel

sealed interface OdontogramEvent{
    data object LoadOdontogram : OdontogramEvent
    data class ToothSelected(
        val toothNumber: Int
    ) : OdontogramEvent
}