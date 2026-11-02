package org.ucb.dentatrack.Appointments.Domain.UseCase

import org.ucb.dentatrack.Appointments.Domain.Model.AppointmentStatus
import org.ucb.dentatrack.Appointments.Domain.Repository.AppointmentRepository

class ChangeAppointmentStatusUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(
        userId: String,
        appointmentId: Int,
        status: AppointmentStatus
    ) {
        require(
            status == AppointmentStatus.COMPLETED ||
                    status == AppointmentStatus.CANCELLED
        )

        repository.changeStatus(
            userId = userId,
            appointmentId = appointmentId,
            status = status
        )
    }
}