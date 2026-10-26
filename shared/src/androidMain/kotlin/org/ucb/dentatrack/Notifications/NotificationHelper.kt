
package org.ucb.dentatrack.Notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import org.ucb.dentatrack.Notifications.Data.Entity.NotificationEntity

object NotificationHelper {

    fun show(
        context: Context,
        item: NotificationEntity
    ): Boolean {

        if (Build.VERSION.SDK_INT >= 33 &&
            context.checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return false

        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        val channelId = DentaTrackMessagingService.CHANNEL_ID

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    "Notificaciones",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        val intent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
            ?: return false

        intent.putExtra("message_id", item.messageId)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
        )

        val pendingIntent = PendingIntent.getActivity(
            context,
            item.messageId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            context,
            channelId
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(item.title)
            .setContentText(item.body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        manager.notify(item.messageId.hashCode(), notification)

        return true
    }
}
