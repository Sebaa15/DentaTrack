package org.ucb.dentatrack.Appointments.Domain.Repository

import org.ucb.dentatrack.Appointments.Domain.Model.Appointment
import org.ucb.dentatrack.Appointments.Domain.Model.AppointmentStatus

interface AppointmentRepository {

    suspend fun getAppointments(
        userId: String
    ): List<Appointment>

    suspend fun saveAppointment(
        userId: String,
        appointment: Appointment
    )

    suspend fun changeStatus(
        userId: String,
        appointmentId: Int,
        status: AppointmentStatus
    )
}