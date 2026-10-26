package org.ucb.dentatrack.Notifications.Data.Repository

import org.ucb.dentatrack.Notifications.Data.DataSource.NotificationFirebaseDataSource
import org.ucb.dentatrack.Notifications.Domain.Repository.NotificationRepository

class NotificationRepositoryImpl(
    private val dataSource:
    NotificationFirebaseDataSource
) : NotificationRepository {

    override suspend fun registerToken(
        userId: String
    ): String {

        return dataSource
            .registerToken(userId)
    }

    override suspend fun unregisterToken(
        userId: String
    ) {

        dataSource
            .unregisterToken(userId)
    }
    override suspend fun saveToken(
        userId: String,
        token: String
    ) {

        dataSource.saveToken(
            userId = userId,
            token = token
        )
    }
}