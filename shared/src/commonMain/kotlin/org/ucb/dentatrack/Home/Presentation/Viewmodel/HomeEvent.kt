package org.ucb.dentatrack.Home.Presentation.Viewmodel

sealed interface HomeEvent {

    data class Start(
        val userId: String
    ) : HomeEvent

    data object OdontogramClicked : HomeEvent
    data object PlanClicked : HomeEvent
}