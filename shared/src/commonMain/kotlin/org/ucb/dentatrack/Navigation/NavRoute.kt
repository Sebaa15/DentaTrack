package org.ucb.dentatrack.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute {

    @Serializable
    data object Login : NavRoute()

    @Serializable
    data object Register : NavRoute()

    @Serializable
    data object Odontogram : NavRoute()

    @Serializable
    data object TreatmentDetail : NavRoute()

    @Serializable
    data object Profile : NavRoute()
}