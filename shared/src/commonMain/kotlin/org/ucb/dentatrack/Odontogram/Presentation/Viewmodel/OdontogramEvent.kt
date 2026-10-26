package org.ucb.dentatrack.Odontogram.Presentation.Viewmodel

sealed interface OdontogramEvent {

    data class Start(val userId: String) : OdontogramEvent
    data class ToothSelected(val toothNumber: Int) : OdontogramEvent

    data object LogoutClicked :OdontogramEvent
    data object SyncClicked : OdontogramEvent
    data object RestoreClicked : OdontogramEvent
}