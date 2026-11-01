package org.ucb.dentatrack.TreatmentPlan.Data.Dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import org.ucb.dentatrack.TreatmentPlan.Data.Entity.TreatmentStepEntity

@Dao
interface TreatmentStepDao {

    @Query(
        """
        SELECT * FROM treatment_steps
        WHERE userId = :userId
        ORDER BY id ASC
        """
    )
    suspend fun getAll(
        userId: Int
    ): List<TreatmentStepEntity>

    @Insert
    suspend fun insert(
        step: TreatmentStepEntity
    ): Long

    @Query(
        """
        UPDATE treatment_steps
        SET title = :title,
            date = :date,
            note = :note,
            status = :status
        WHERE id = :id
          AND userId = :userId
        """
    )
    suspend fun update(
        id: Int,
        userId: Int,
        title: String,
        date: String,
        note: String,
        status: String
    ): Int
}