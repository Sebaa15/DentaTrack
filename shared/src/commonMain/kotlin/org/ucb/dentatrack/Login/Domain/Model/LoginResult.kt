package org.ucb.dentatrack.Login.Domain.Model

sealed interface LoginResult {

    data object Success : LoginResult

    data class Error(
        val type: LoginError
    ) : LoginResult
}

enum class LoginError {
    EMPTY_EMAIL,
    INVALID_EMAIL,
    EMPTY_PASSWORD,
    SHORT_PASSWORD,
    INVALID_CREDENTIALS
}