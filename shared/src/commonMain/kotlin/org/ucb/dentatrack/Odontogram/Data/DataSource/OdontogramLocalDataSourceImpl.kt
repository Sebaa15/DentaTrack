package org.ucb.dentatrack.Odontogram.Data.DataSource

import org.ucb.dentatrack.Odontogram.Data.Dao.ToothDao
import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity

class OdontogramLocalDataSourceImpl(
    private val dao: ToothDao
) : OdontogramLocalDataSource {

    override suspend fun getTeeth():
            List<ToothEntity> {

        val savedTeeth =
            dao.getAll()

        if (savedTeeth.isNotEmpty()) {
            return savedTeeth
        }

        val initialTeeth =
            listOf(

                ToothEntity(
                    number = 18,
                    status = "HEALTHY"
                ),

                ToothEntity(
                    number = 17,
                    status = "HEALTHY"
                ),

                ToothEntity(
                    number = 16,
                    status = "IN_TREATMENT",
                    treatment = "Restauracion"
                ),

                ToothEntity(
                    number = 15,
                    status = "OBSERVATION",
                    treatment = "Control"
                ),

                ToothEntity(
                    number = 14,
                    status = "COMPLETED",
                    treatment = "Limpieza"
                ),

                ToothEntity(
                    number = 13,
                    status = "HEALTHY"
                ),

                ToothEntity(
                    number = 12,
                    status = "HEALTHY"
                ),

                ToothEntity(
                    number = 11,
                    status = "HEALTHY"
                )
            )

        dao.saveAll(initialTeeth)

        return dao.getAll()
    }

    override suspend fun saveTeeth(
        teeth: List<ToothEntity>
    ) {
        dao.saveAll(teeth)
    }
}