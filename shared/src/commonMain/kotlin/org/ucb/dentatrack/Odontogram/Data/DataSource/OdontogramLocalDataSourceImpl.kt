package org.ucb.dentatrack.Odontogram.Data.DataSource

import org.ucb.dentatrack.Odontogram.Data.Dao.ToothDao
import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity

class OdontogramLocalDataSourceImpl(
    private val dao: ToothDao
) : OdontogramLocalDataSource {

    override suspend fun getTeeth(userId: Int): List<ToothEntity> {

        val savedTeeth = dao.getAll(userId)

        if (savedTeeth.isNotEmpty()) {
            return savedTeeth
        }

        val initialTeeth =
            listOf(

                ToothEntity(
                    userId = userId,
                    number = 18,
                    status = "HEALTHY"
                ),

                ToothEntity(
                    userId = userId,
                    number = 17,
                    status = "HEALTHY"
                ),

                ToothEntity(
                    userId = userId,
                    number = 16,
                    status = "IN_TREATMENT",
                    treatment = "Restauracion"
                ),

                ToothEntity(
                    userId = userId,
                    number = 15,
                    status = "OBSERVATION",
                    treatment = "Control"
                ),

                ToothEntity(
                    userId = userId,
                    number = 14,
                    status = "COMPLETED",
                    treatment = "Limpieza"
                ),

                ToothEntity(
                    userId = userId,
                    number = 13,
                    status = "HEALTHY"
                ),

                ToothEntity(
                    userId = userId,
                    number = 12,
                    status = "HEALTHY"
                ),

                ToothEntity(
                    userId = userId,
                    number = 11,
                    status = "HEALTHY"
                )
            )

        dao.saveAll(initialTeeth)

        return dao.getAll(userId)
    }

    override suspend fun saveTeeth(
        teeth: List<ToothEntity>
    ) {
        dao.saveAll(teeth)
    }
    override suspend fun insertMissingTeeth(
        teeth: List<ToothEntity>
    ): Int {
        return dao.insertMissing(teeth)
            .count { it != -1L }
    }
}