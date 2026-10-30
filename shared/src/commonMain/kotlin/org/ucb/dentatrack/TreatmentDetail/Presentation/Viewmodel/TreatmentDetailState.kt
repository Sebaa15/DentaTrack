package org.ucb.dentatrack.TreatmentDetail.Presentation.Viewmodel

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth

data class TreatmentDetailState(
    val toothNumber: Int = 0,
    val tooth: Tooth? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)