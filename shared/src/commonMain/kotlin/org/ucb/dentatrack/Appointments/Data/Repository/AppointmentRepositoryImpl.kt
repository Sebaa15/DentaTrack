package org.ucb.dentatrack.Appointments.Data.Repository

import org.ucb.dentatrack.Appointments.Data.Dao.AppointmentDao
import org.ucb.dentatrack.Appointments.Data.Entity.AppointmentEntity
import org.ucb.dentatrack.Appointments.Domain.Model.Appointment
import org.ucb.dentatrack.Appointments.Domain.Model.AppointmentStatus
import org.ucb.dentatrack.Appointments.Domain.Repository.AppointmentRepository

class AppointmentRepositoryImpl(
    private val dao: AppointmentDao
) : AppointmentRepository {

    override suspend fun getAppointments(
        userId: String
    ): List<Appointment> {

        val localId = userId.toInt()

        return dao.getAll(localId).map { entity ->
            Appointment(
                id = entity.id,
                reason = entity.reason,
                doctor = entity.doctor,
                date = entity.date,
                time = entity.time,
                status = AppointmentStatus.valueOf(
                    entity.status
                )
            )
        }
    }

    override suspend fun saveAppointment(
        userId: String,
        appointment: Appointment
    ) {
        val localId = userId.toInt()

        if (appointment.id == 0) {

            dao.insert(
                AppointmentEntity(
                    userId = localId,
                    reason = appointment.reason,
                    doctor = appointment.doctor,
                    date = appointment.date,
                    time = appointment.time,
                    status = AppointmentStatus.SCHEDULED.name
                )
            )

        } else {

            val updated = dao.update(
                id = appointment.id,
                userId = localId,
                reason = appointment.reason,
                doctor = appointment.doctor,
                date = appointment.date,
                time = appointment.time
            )

            if (updated == 0) {
                error("La cita no se puede reprogramar")
            }
        }
    }

    override suspend fun changeStatus(
        userId: String,
        appointmentId: Int,
        status: AppointmentStatus
    ) {
        val updated = dao.changeStatus(
            id = appointmentId,
            userId = userId.toInt(),
            status = status.name
        )

        if (updated == 0) {
            error("La cita ya no está agendada")
        }
    }
}