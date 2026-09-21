package org.ucb.dentatrack.Odontogram.Presentation.Viewmodel

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth

data class OdontogramState(
    val teeth: List<Tooth> = emptyList(),
    val selectedTooth: Tooth? = null,
    val isLoading: Boolean = false
)