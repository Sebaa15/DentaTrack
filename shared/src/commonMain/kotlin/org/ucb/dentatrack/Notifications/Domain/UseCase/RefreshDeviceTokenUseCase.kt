package org.ucb.dentatrack.Notifications.Domain.UseCase

import org.ucb.dentatrack.Login.Domain.Repository.LoginRepository
import org.ucb.dentatrack.Notifications.Domain.Repository.NotificationRepository

class RefreshDeviceTokenUseCase(
    private val loginRepository:
    LoginRepository,

    private val notificationRepository:
    NotificationRepository
) {

    suspend operator fun invoke(
        token: String
    ) {

        val user =
            loginRepository
                .getCurrentUser()
                ?: return

        notificationRepository
            .saveToken(
                userId = user.id,
                token = token
            )
    }
}