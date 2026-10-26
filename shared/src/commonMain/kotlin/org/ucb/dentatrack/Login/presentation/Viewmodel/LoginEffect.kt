package org.ucb.dentatrack.Login.presentation.Viewmodel

sealed interface LoginEffect {

    data class NavigateToOdontogram(
        val userId: String
    ) : LoginEffect

    data class ShowMessage(
        val message: String
    ) : LoginEffect
}