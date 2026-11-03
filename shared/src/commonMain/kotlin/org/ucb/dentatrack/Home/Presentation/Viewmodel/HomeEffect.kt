package org.ucb.dentatrack.Home.Presentation.Viewmodel

sealed interface HomeEffect {

    data class NavigateToOdontogram(
        val userId: String
    ) : HomeEffect
    data class NavigateToPlan(
        val userId: String
    ) : HomeEffect
    data object NavigateToLogin : HomeEffect
    data class NavigateToAppointments(
        val userId: String
    ) : HomeEffect
    data class NavigateToCare(
        val userId: String
    ) : HomeEffect
}