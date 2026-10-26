package org.ucb.dentatrack.Notifications.Data.DataSource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.messaging.messaging
import org.ucb.dentatrack.Notifications.Data.Dto.DeviceTokenDto

class NotificationFirebaseDataSource {

    private val database by lazy {
        Firebase.database
    }

    private val tokensRef by lazy {
        database.reference(
            "device_tokens"
        )
    }

    suspend fun registerToken(userId: String): String {

        val token = Firebase.messaging.getToken()
        saveToken(userId, token)
        return token
    }

    suspend fun saveToken(
        userId: String,
        token: String
    ) {

        tokensRef
            .child(userId)
            .setValue(
                DeviceTokenDto(
                    userId = userId,
                    token = token
                )
            )
    }

    suspend fun unregisterToken(
        userId: String
    ) {

        tokensRef
            .child(userId)
            .removeValue()

        Firebase.messaging
            .deleteToken()
    }
}