package org.ucb.dentatrack.Appointments.Domain.Model

data class Appointment(
    val id: Int = 0,
    val reason: String,
    val doctor: String,
    val date: String,
    val time: String,
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED
)

enum class AppointmentStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED
}