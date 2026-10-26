package org.ucb.dentatrack.Notifications

import kotlinx.coroutines.runBlocking
import org.ucb.dentatrack.Notifications.Data.Dao.NotificationDao
import org.ucb.dentatrack.Notifications.Data.Entity.NotificationEntity
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.ucb.dentatrack.Notifications.Domain.UseCase.RefreshDeviceTokenUseCase

class DentaTrackMessagingService :
    FirebaseMessagingService(),
    KoinComponent {

    private val refreshDeviceTokenUseCase:
            RefreshDeviceTokenUseCase by inject()

    private val serviceScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO

    )
    private val notificationDao: NotificationDao by inject()

    // Firebase genera o renueva el token
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "Nuevo token FCM generado")

        serviceScope.launch {
            try {
                refreshDeviceTokenUseCase(token)

                Log.d(
                    TAG,
                    "Actualización de token procesada"
                )
            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "Error actualizando token FCM",
                    e
                )
            }
        }
    }

    // Recepción de notificaciones
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val id = message.data["message_id"]
            ?: message.messageId
            ?: System.currentTimeMillis().toString()

        val title = message.data["title"]
            ?: message.notification?.title
            ?: "Notificación"

        val body = message.data["body"]
            ?: message.notification?.body
            ?: return

        runBlocking(Dispatchers.IO) {

            if (notificationDao.findById(id) != null) {
                return@runBlocking
            }

            val notification = NotificationEntity(
                messageId = id,
                title = title,
                body = body,
                receivedAt = System.currentTimeMillis()
            )

            notificationDao.save(notification)

            if (NotificationPreferences.canShow(
                    notificationDao.lastDisplayedAt()
                )
            ) {
                if (NotificationHelper.show(
                        this@DentaTrackMessagingService,
                        notification
                    )
                ) {
                    notificationDao.markDisplayed(
                        id,
                        System.currentTimeMillis()
                    )
                }
            }
        }
    }


    companion object {
        private const val TAG = "DentaTrackFCM"

        const val CHANNEL_ID =
            "dentatrack_notifications"
    }
}
