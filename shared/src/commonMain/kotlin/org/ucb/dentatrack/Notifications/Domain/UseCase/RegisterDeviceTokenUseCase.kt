package org.ucb.dentatrack.Notifications.Domain.UseCase

import org.ucb.dentatrack.Notifications.Domain.Repository.NotificationRepository

class RegisterDeviceTokenUseCase(
    private val repository:
    NotificationRepository
) {

    suspend operator fun invoke(
        userId: String
    ) =
        repository.registerToken(
            userId
        )
}