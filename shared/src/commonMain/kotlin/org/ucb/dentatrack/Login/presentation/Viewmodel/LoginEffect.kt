package org.ucb.dentatrack.Login.presentation.Viewmodel

sealed interface LoginEffect {

    data object NavigateToOdontogram : LoginEffect

    data class ShowMessage(
        val message: String
    ) : LoginEffect
}