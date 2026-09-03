package org.ucb.dentatrack.Login.presentation.State

sealed interface LoginIntent {

    data class EmailChanged(
        val email: String
    ) : LoginIntent

    data class PasswordChanged(
        val password: String
    ) : LoginIntent

    data object LoginClicked : LoginIntent
}