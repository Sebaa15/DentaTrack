package org.ucb.dentatrack.Appointments.Presentation.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import org.ucb.dentatrack.Login.Domain.UseCase.GetCurrentUserUseCase
import org.ucb.dentatrack.Appointments.Domain.Model.Appointment
import org.ucb.dentatrack.Appointments.Domain.Model.AppointmentStatus
import org.ucb.dentatrack.Appointments.Domain.UseCase.GetAppointmentsUseCase
import org.ucb.dentatrack.Appointments.Domain.UseCase.SaveAppointmentUseCase
import org.ucb.dentatrack.Appointments.Domain.UseCase.ChangeAppointmentStatusUseCase

class AppointmentsViewModel(
    private val getAppointmentsUseCase: GetAppointmentsUseCase,
    private val saveAppointmentUseCase: SaveAppointmentUseCase,
    private val changeAppointmentStatusUseCase: ChangeAppointmentStatusUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AppointmentsState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AppointmentsEffect>()
    val effect = _effect.asSharedFlow()

    fun emitEvent(event: AppointmentsEvent) {

        when (event) {

            is AppointmentsEvent.Start -> {
                loadAppointments(event.userId)
            }

            is AppointmentsEvent.ReasonChanged -> {
                if (!state.value.isSaving) {
                    _state.update {
                        it.copy(reason = event.value, error = null)
                    }
                }
            }

            is AppointmentsEvent.DoctorChanged -> {
                if (!state.value.isSaving) {
                    _state.update {
                        it.copy(doctor = event.value, error = null)
                    }
                }
            }

            is AppointmentsEvent.DateChanged -> {
                if (!state.value.isSaving) {
                    _state.update {
                        it.copy(date = event.value, error = null)
                    }
                }
            }

            is AppointmentsEvent.TimeChanged -> {
                if (!state.value.isSaving) {
                    _state.update {
                        it.copy(time = event.value, error = null)
                    }
                }
            }

            is AppointmentsEvent.EditClicked -> {
                if (!state.value.isSaving) {
                    val appointment = state.value.appointments.find {
                        it.id == event.id &&
                                it.status == AppointmentStatus.SCHEDULED
                    }

                    if (appointment != null) {
                        _state.update {
                            it.copy(
                                editingId = appointment.id,
                                reason = appointment.reason,
                                doctor = appointment.doctor,
                                date = appointment.date,
                                time = appointment.time,
                                error = null,
                                message = "Modifica los datos y guarda la cita"
                            )
                        }
                    }
                }
            }

            AppointmentsEvent.NewClicked -> {
                if (!state.value.isSaving) {
                    clearForm()
                }
            }

            AppointmentsEvent.SaveClicked -> {
                saveAppointment()
            }

            is AppointmentsEvent.CancelRequested -> {
                if (!state.value.isSaving &&
                    state.value.appointments.any {
                        it.id == event.id &&
                                it.status == AppointmentStatus.SCHEDULED
                    }
                ) {
                    _state.update {
                        it.copy(pendingCancelId = event.id)
                    }
                }
            }

            AppointmentsEvent.CancelDismissed -> {
                _state.update {
                    it.copy(pendingCancelId = null)
                }
            }

            AppointmentsEvent.CancelConfirmed -> {
                val id = state.value.pendingCancelId

                if (id != null) {
                    changeStatus(
                        id,
                        AppointmentStatus.CANCELLED
                    )
                }
            }

            is AppointmentsEvent.CompleteClicked -> {
                changeStatus(
                    event.id,
                    AppointmentStatus.COMPLETED
                )
            }

            AppointmentsEvent.BackClicked -> {
                viewModelScope.launch {
                    _effect.emit(
                        AppointmentsEffect.NavigateBack
                    )
                }
            }
        }
    }

    private fun loadAppointments(userId: String) {

        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val currentUser = getCurrentUserUseCase()

                if (currentUser?.id != userId) {
                    _state.update {
                        it.copy(
                            userId = null,
                            isLoading = false,
                            error = "Sesión no válida"
                        )
                    }
                    return@launch
                }

                val appointments =
                    getAppointmentsUseCase(userId)

                _state.update {
                    it.copy(
                        userId = userId,
                        appointments = appointments,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                println("APPOINTMENTS_LOAD_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "No se pudieron cargar las citas"
                    )
                }
            }
        }
    }

    private fun saveAppointment() {

        val current = state.value
        val userId = current.userId ?: return

        if (current.isLoading || current.isSaving) return

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isSaving = true,
                    error = null,
                    message = null
                )
            }

            try {
                saveAppointmentUseCase(
                    userId = userId,
                    appointment = Appointment(
                        id = current.editingId,
                        reason = current.reason,
                        doctor = current.doctor,
                        date = current.date,
                        time = current.time
                    )
                )

                val appointments =
                    getAppointmentsUseCase(userId)

                _state.update {
                    it.copy(
                        appointments = appointments,
                        editingId = 0,
                        reason = "",
                        doctor = "",
                        date = "",
                        time = "",
                        isSaving = false,
                        message = "Cita guardada correctamente",
                        error = null
                    )
                }

            } catch (e: Exception) {
                println("APPOINTMENTS_SAVE_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isSaving = false,
                        error = e.message ?: "Error al guardar la cita"
                    )
                }
            }
        }
    }

    private fun changeStatus(
        appointmentId: Int,
        status: AppointmentStatus
    ) {
        val current = state.value
        val userId = current.userId ?: return

        if (current.isSaving || current.isLoading) return

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isSaving = true,
                    pendingCancelId = null,
                    error = null
                )
            }

            try {
                changeAppointmentStatusUseCase(
                    userId = userId,
                    appointmentId = appointmentId,
                    status = status
                )

                val appointments =
                    getAppointmentsUseCase(userId)

                _state.update {
                    it.copy(
                        appointments = appointments,
                        editingId = if (it.editingId == appointmentId)
                            0 else it.editingId,
                        reason = if (it.editingId == appointmentId)
                            "" else it.reason,
                        doctor = if (it.editingId == appointmentId)
                            "" else it.doctor,
                        date = if (it.editingId == appointmentId)
                            "" else it.date,
                        time = if (it.editingId == appointmentId)
                            "" else it.time,
                        isSaving = false,
                        message = if (status == AppointmentStatus.CANCELLED)
                            "Cita cancelada"
                        else
                            "Cita marcada como realizada"
                    )
                }

            } catch (e: Exception) {
                println("APPOINTMENTS_STATUS_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isSaving = false,
                        error = "No se pudo actualizar la cita"
                    )
                }
            }
        }
    }

    private fun clearForm() {
        _state.update {
            it.copy(
                editingId = 0,
                reason = "",
                doctor = "",
                date = "",
                time = "",
                error = null,
                message = null
            )
        }
    }
}