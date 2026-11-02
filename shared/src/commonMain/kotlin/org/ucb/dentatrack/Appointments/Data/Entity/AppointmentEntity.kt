package org.ucb.dentatrack.Appointments.Data.Entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "appointments")
data class AppointmentEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: Int,
    val reason: String,
    val doctor: String,
    val date: String,
    val time: String,
    val status: String
)