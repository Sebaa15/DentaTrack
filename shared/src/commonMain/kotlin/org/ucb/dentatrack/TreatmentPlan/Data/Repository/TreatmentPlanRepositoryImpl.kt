package org.ucb.dentatrack.TreatmentPlan.Data.Repository

import org.ucb.dentatrack.TreatmentPlan.Data.Dao.TreatmentStepDao
import org.ucb.dentatrack.TreatmentPlan.Data.Entity.TreatmentStepEntity
import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStep
import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStepStatus
import org.ucb.dentatrack.TreatmentPlan.Domain.Repository.TreatmentPlanRepository

class TreatmentPlanRepositoryImpl(
    private val dao: TreatmentStepDao
) : TreatmentPlanRepository {

    override suspend fun getSteps(
        userId: String
    ): List<TreatmentStep> {

        val localId = userId.toInt()

        return dao.getAll(localId).map { entity ->

            TreatmentStep(
                id = entity.id,
                title = entity.title,
                date = entity.date,
                note = entity.note,
                status = TreatmentStepStatus.valueOf(
                    entity.status
                )
            )
        }
    }

    override suspend fun saveStep(
        userId: String,
        step: TreatmentStep
    ) {
        val localId = userId.toInt()

        if (step.id == 0) {

            dao.insert(
                TreatmentStepEntity(
                    userId = localId,
                    title = step.title,
                    date = step.date,
                    note = step.note,
                    status = step.status.name
                )
            )

        } else {

            val updated = dao.update(
                id = step.id,
                userId = localId,
                title = step.title,
                date = step.date,
                note = step.note,
                status = step.status.name
            )

            if (updated == 0) {
                error("No se encontró la etapa")
            }
        }
    }
}