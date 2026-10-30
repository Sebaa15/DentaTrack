package org.ucb.dentatrack.Odontogram.Presentation.Viewmodel

import org.ucb.dentatrack.Odontogram.Domain.Model.ToothStatus

sealed interface OdontogramEvent {

    data class Start(val userId: String) : OdontogramEvent
    data class ToothSelected(val toothNumber: Int) : OdontogramEvent

    data object LogoutClicked :OdontogramEvent
    data object SyncClicked : OdontogramEvent
    data object RestoreClicked : OdontogramEvent
    data object ViewTreatmentClicked : OdontogramEvent
    data class StatusChanged(
        val status: ToothStatus
    ) : OdontogramEvent

    data class TreatmentChanged(
        val treatment: String
    ) : OdontogramEvent

    data object SaveClicked : OdontogramEvent
}