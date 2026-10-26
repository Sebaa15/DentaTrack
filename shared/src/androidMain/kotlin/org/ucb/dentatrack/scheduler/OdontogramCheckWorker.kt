package org.ucb.dentatrack.scheduler

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.ucb.dentatrack.Login.Domain.Repository.LoginRepository
import org.ucb.dentatrack.Odontogram.Domain.UseCase.GetOdontogramUseCase

class OdontogramCheckWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val getOdontogramUseCase:
            GetOdontogramUseCase by inject()
    private val loginRepository:
            LoginRepository by inject()
    override suspend fun doWork(): Result {
        return try {

            val userId = inputData.getString("userId")
                ?: return Result.success()

            val usuarioActual =
                loginRepository.getCurrentUser()

            if (usuarioActual?.id != userId) {
                Log.d("DentaWork", "Sin sesión activa")
                return Result.success()
            }

            val teeth = getOdontogramUseCase(userId)

            Log.d(
                "DentaWork",
                "Dientes locales: ${teeth.size}"
            )

            Result.success()

        } catch (e: Exception) {

            Log.e("DentaWork", "Error en Worker", e)

            Result.retry()
        }
    }
}
