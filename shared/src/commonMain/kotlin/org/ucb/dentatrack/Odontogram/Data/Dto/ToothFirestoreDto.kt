package org.ucb.dentatrack.Odontogram.Data.Dto

import kotlinx.serialization.Serializable

@Serializable
data class ToothFirestoreDto(
    val number: Int = 0,
    val status: String = "",
    val treatment: String? = null
)