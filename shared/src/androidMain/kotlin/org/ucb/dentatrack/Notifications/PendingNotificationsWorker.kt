package org.ucb.dentatrack.Notifications

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.ucb.dentatrack.Notifications.Data.Dao.NotificationDao

class PendingNotificationsWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val dao: NotificationDao by inject()

    override suspend fun doWork(): Result {
        return try {
            for (item in dao.pending()) {

                if (!NotificationPreferences.canShow(
                        dao.lastDisplayedAt()
                    )
                ) break

                if (NotificationHelper.show(applicationContext, item)) {
                    dao.markDisplayed(
                        item.messageId,
                        System.currentTimeMillis()
                    )
                }
            }

            Result.success()

        } catch (e: Exception) {
            Log.e("DentaNotifications", "Error en Worker", e)
            Result.retry()
        }
    }
}
fun schedulePendingNotifications(context: Context) {

    val request =
        PeriodicWorkRequestBuilder<PendingNotificationsWorker>(
            15,
            TimeUnit.MINUTES
        ).build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            "pending_notifications",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
}