package org.ucb.dentatrack.feature.login.presentation.state

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)