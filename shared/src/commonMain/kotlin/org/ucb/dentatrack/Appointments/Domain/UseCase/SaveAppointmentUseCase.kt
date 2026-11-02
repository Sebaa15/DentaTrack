package org.ucb.dentatrack.Appointments.Domain.UseCase

import org.ucb.dentatrack.Appointments.Domain.Model.Appointment
import org.ucb.dentatrack.Appointments.Domain.Repository.AppointmentRepository

class SaveAppointmentUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(
        userId: String,
        appointment: Appointment
    ) {
        require(appointment.reason.isNotBlank()) {
            "Ingrese el motivo de la cita"
        }

        require(appointment.doctor.isNotBlank()) {
            "Ingrese el nombre del odontólogo"
        }

        require(validDate(appointment.date)) {
            "Fecha inválida. Use AAAA-MM-DD"
        }

        require(validTime(appointment.time)) {
            "Hora inválida. Use HH:mm"
        }

        repository.saveAppointment(
            userId,
            appointment.copy(
                reason = appointment.reason.trim(),
                doctor = appointment.doctor.trim()
            )
        )
    }

    private fun validDate(date: String): Boolean {
        if (!Regex("""\d{4}-\d{2}-\d{2}""").matches(date)) {
            return false
        }

        val year = date.substring(0, 4).toInt()
        val month = date.substring(5, 7).toInt()
        val day = date.substring(8, 10).toInt()

        if (year == 0 || month !in 1..12) return false

        val leap = year % 400 == 0 ||
                (year % 4 == 0 && year % 100 != 0)

        val days = when (month) {
            2 -> if (leap) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

        return day in 1..days
    }

    private fun validTime(time: String): Boolean {
        if (!Regex("""\d{2}:\d{2}""").matches(time)) {
            return false
        }

        val hour = time.substring(0, 2).toInt()
        val minute = time.substring(3, 5).toInt()

        return hour in 0..23 && minute in 0..59
    }
}