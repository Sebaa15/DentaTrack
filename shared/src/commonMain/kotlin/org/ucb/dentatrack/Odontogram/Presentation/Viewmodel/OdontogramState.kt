package org.ucb.dentatrack.Odontogram.Presentation.Viewmodel

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Model.ToothStatus

data class OdontogramState(

    val teeth: List<Tooth> = emptyList(),
    val selectedTooth: Tooth? = null,
    val isLoading: Boolean = false,
    val title: String = "MI ODONTOGRAMA",
    val treatmentDetailEnabled: Boolean = true,
    val isSyncing: Boolean = false,
    val syncMessage: String? = null,
    val editingStatus: ToothStatus? = null,
    val editingTreatment: String = "",
    val isSaving: Boolean = false,
    val saveMessage: String? = null
)