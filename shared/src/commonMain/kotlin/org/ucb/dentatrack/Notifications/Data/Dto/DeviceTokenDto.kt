package org.ucb.dentatrack.Notifications.Data.Dto

import kotlinx.serialization.Serializable

@Serializable
data class DeviceTokenDto(
    val userId: String = "",
    val token: String = "",
    val updatedAt: Long = 0L
)