package org.ucb.dentatrack.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute {

    @Serializable
    data object Login : NavRoute()

    @Serializable
    data object Register : NavRoute()

    @Serializable
    data class Odontogram(
        val userId: String
    ) : NavRoute()

    @Serializable
    data class TreatmentDetail(
        val toothNumber: Int
    ): NavRoute()

    @Serializable
    data object Profile : NavRoute()
    @Serializable
    data object Settings : NavRoute()

    @Serializable
    data object NotificationHistory : NavRoute()

    @Serializable
    data class NotificationDetail(
        val messageId: String
    ) : NavRoute()
}