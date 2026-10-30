package org.ucb.dentatrack.Home.Presentation.Viewmodel

sealed interface HomeEffect {

    data class NavigateToOdontogram(
        val userId: String
    ) : HomeEffect

    data object NavigateToLogin : HomeEffect
}