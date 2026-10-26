package org.ucb.dentatrack.Notifications.Data.DataSource

import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlin.time.Clock
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
        val uid = Firebase.auth.currentUser?.uid
            ?: Firebase.auth.signInAnonymously().user?.uid
            ?: error("No se pudo autenticar en Firebase")

        // Firestore: requerido por E100-4
        Firebase.firestore
            .collection("usuarios")
            .document(uid)
            .set(
                DeviceTokenDto(
                    userId = uid,
                    token = token,
                    updatedAt = Clock.System.now()
                        .toEpochMilliseconds()
                )
            )

        // Realtime Database: funcionalidad anterior
        runCatching {
            tokensRef.child(userId).setValue(
                DeviceTokenDto(
                    userId = userId,
                    token = token
                )
            )
        }.onFailure {
            println("Error guardando token en RTDB: ${it.message}")
        }
    }

    suspend fun unregisterToken(
        userId: String
    ) {

        tokensRef
            .child(userId)
            .removeValue()
        Firebase.auth.currentUser?.uid?.let { uid ->
            Firebase.firestore
                .collection("usuarios")
                .document(uid)
                .delete()
        }
        Firebase.messaging
            .deleteToken()
    }
}