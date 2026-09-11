package org.ucb.dentatrack.Login.presentation.Viewmodel

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

