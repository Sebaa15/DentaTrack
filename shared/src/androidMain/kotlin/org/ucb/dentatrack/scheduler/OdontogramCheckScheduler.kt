
package org.ucb.dentatrack.scheduler

import android.content.Context
import android.util.Log
import androidx.work.*
import org.ucb.dentatrack.sheduler.OdontogramWorkScheduler
import java.util.concurrent.TimeUnit

class OdontogramCheckScheduler(
    private val context: Context
) : OdontogramWorkScheduler {

    private val workManager =
        WorkManager.getInstance(context)

    override fun schedule(userId: String) {

        val request =
            PeriodicWorkRequestBuilder<OdontogramCheckWorker>(
                15,
                TimeUnit.MINUTES
            )
                .setInputData(
                    workDataOf("userId" to userId)
                )
                .build()

        workManager.enqueueUniquePeriodicWork(
            "odontogramPeriodicCheck",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )

        Log.d("DentaWork", "Trabajo programado")
    }

    override fun cancel() {
        workManager.cancelUniqueWork(
            "odontogramPeriodicCheck"
        )

        Log.d("DentaWork", "Trabajo cancelado")
    }
}
