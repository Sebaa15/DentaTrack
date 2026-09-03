package org.ucb.dentatrack.Register.Presentation.State

data class RegisterState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val error: String? = null,
    val isRegistered: Boolean = false
)