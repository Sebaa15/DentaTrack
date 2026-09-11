package org.ucb.dentatrack.Login.presentation.Viewmodel

sealed interface LoginEvent {

    data class EmailChanged(
        val email: String
    ) : LoginEvent

    data class PasswordChanged(
        val password: String
    ) : LoginEvent

    data object LoginClicked : LoginEvent
}