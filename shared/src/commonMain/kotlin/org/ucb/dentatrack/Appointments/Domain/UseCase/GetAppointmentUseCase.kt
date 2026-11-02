package org.ucb.dentatrack.Appointments.Domain.UseCase

import org.ucb.dentatrack.Appointments.Domain.Repository.AppointmentRepository

class GetAppointmentsUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(userId: String) =
        repository.getAppointments(userId)
}