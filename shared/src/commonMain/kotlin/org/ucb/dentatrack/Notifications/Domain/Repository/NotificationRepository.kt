package org.ucb.dentatrack.Notifications.Domain.Repository

interface NotificationRepository {

    suspend fun registerToken(
        userId: String
    ): String

    suspend fun unregisterToken(
        userId: String
    )
    suspend fun saveToken(
        userId: String,
        token: String
    )
}