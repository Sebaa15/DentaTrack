package org.ucb.dentatrack.Appointments.Data.Dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import org.ucb.dentatrack.Appointments.Data.Entity.AppointmentEntity

@Dao
interface AppointmentDao {

    @Query("""
        SELECT * FROM appointments
        WHERE userId = :userId
        ORDER BY date ASC, time ASC, id ASC
    """)
    suspend fun getAll(
        userId: Int
    ): List<AppointmentEntity>

    @Insert
    suspend fun insert(
        appointment: AppointmentEntity
    ): Long

    @Query("""
        UPDATE appointments
        SET reason = :reason,
            doctor = :doctor,
            date = :date,
            time = :time
        WHERE id = :id
          AND userId = :userId
          AND status = 'SCHEDULED'
    """)
    suspend fun update(
        id: Int,
        userId: Int,
        reason: String,
        doctor: String,
        date: String,
        time: String
    ): Int

    @Query("""
        UPDATE appointments
        SET status = :status
        WHERE id = :id
          AND userId = :userId
          AND status = 'SCHEDULED'
    """)
    suspend fun changeStatus(
        id: Int,
        userId: Int,
        status: String
    ): Int
}