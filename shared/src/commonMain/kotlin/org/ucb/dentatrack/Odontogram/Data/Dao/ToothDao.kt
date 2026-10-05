package org.ucb.dentatrack.Odontogram.Data.Dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity

@Dao
interface ToothDao {

    @Query(
        "SELECT * FROM teeth ORDER BY number DESC"
    )
    suspend fun getAll():
            List<ToothEntity>

    @Upsert
    suspend fun saveAll(
        teeth: List<ToothEntity>
    )
}