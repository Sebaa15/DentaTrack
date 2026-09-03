package org.ucb.dentatrack.Login.presentation.State

sealed interface LoginEffect {

    data object NavigateToOdontogram : LoginEffect

    data class ShowMessage(
        val message: String
    ) : LoginEffect
}