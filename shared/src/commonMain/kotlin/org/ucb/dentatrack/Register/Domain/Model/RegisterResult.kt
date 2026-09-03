package org.ucb.dentatrack.Register.Domain.Model

sealed interface RegisterResult {

    data object Success : RegisterResult

    data class Error(
        val type: RegisterError
    ) : RegisterResult
}

enum class RegisterError {
    EMPTY_NAME,
    EMPTY_EMAIL,
    INVALID_EMAIL,
    EMPTY_PASSWORD,
    SHORT_PASSWORD,
    PASSWORDS_DO_NOT_MATCH,
    USER_ALREADY_EXISTS
}