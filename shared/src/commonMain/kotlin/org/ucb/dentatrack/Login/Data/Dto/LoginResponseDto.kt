package org.ucb.dentatrack.Login.Data.Dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val id: String,
    val fullName: String,
    val email: String
)