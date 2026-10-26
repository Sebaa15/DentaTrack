
package org.ucb.dentatrack.Notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
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
    override fun onMessageReceived(
        message: RemoteMessage
    ) {
        super.onMessageReceived(message)

        val title = message.notification?.title
            ?: message.data["title"]
            ?: "DentaTrack"

        val body = message.notification?.body
            ?: message.data["body"]
            ?: return

        Log.d(TAG, "Notificación recibida")

        showNotification(title, body)
    }

    private fun showNotification(
        title: String,
        body: String
    ) {
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.w(TAG, "Permiso de notificaciones denegado")
            return
        }

        val manager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notificaciones DentaTrack",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(
            this,
            CHANNEL_ID
        )
            .setSmallIcon(
                android.R.drawable.ic_dialog_info
            )
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .build()

        manager.notify(
            System.currentTimeMillis().toInt(),
            notification
        )
    }

    companion object {
        private const val TAG = "DentaTrackFCM"

        const val CHANNEL_ID =
            "dentatrack_notifications"
    }
}
