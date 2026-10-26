package org.ucb.dentatrack.Odontogram.Data.Dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Upsert
import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity

@Dao
interface ToothDao {

    @Query(
        "SELECT * FROM teeth WHERE userId = :userId ORDER BY number DESC"
    )
    suspend fun getAll(userId: Int): List<ToothEntity>

    @Upsert
    suspend fun saveAll(
        teeth: List<ToothEntity>
    )
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMissing(
        teeth: List<ToothEntity>
    ): List<Long>
}