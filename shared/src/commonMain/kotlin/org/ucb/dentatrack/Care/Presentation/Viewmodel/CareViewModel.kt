package org.ucb.dentatrack.Care.Presentation.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import org.ucb.dentatrack.Login.Domain.UseCase.GetCurrentUserUseCase
import org.ucb.dentatrack.Care.Domain.UseCase.GetDailyCareUseCase
import org.ucb.dentatrack.Care.Domain.UseCase.SaveCareItemUseCase
import org.ucb.dentatrack.Care.Domain.UseCase.SetCareCompletedUseCase
import org.ucb.dentatrack.TreatmentPlan.Domain.UseCase.GetTreatmentPlanUseCase
import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStepStatus
import org.ucb.dentatrack.Appointments.Domain.UseCase.GetAppointmentsUseCase
import org.ucb.dentatrack.Appointments.Domain.Model.Appointment
import org.ucb.dentatrack.Appointments.Domain.Model.AppointmentStatus

class CareViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getDailyCareUseCase: GetDailyCareUseCase,
    private val saveCareItemUseCase: SaveCareItemUseCase,
    private val setCareCompletedUseCase: SetCareCompletedUseCase,
    private val getTreatmentPlanUseCase: GetTreatmentPlanUseCase,
    private val getAppointmentsUseCase: GetAppointmentsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CareState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CareEffect>()
    val effect = _effect.asSharedFlow()

    fun emitEvent(event: CareEvent) {
        when (event) {

            is CareEvent.Start -> {
                loadCare(event.userId, event.date)
            }

            is CareEvent.TitleChanged -> {
                if (!state.value.isSaving) {
                    _state.update {
                        it.copy(
                            newTitle = event.value,
                            error = null
                        )
                    }
                }
            }

            is CareEvent.NoteChanged -> {
                if (!state.value.isSaving) {
                    _state.update {
                        it.copy(newNote = event.value)
                    }
                }
            }

            is CareEvent.CheckedChanged -> {
                changeCompleted(
                    event.careId,
                    event.completed
                )
            }

            CareEvent.AddClicked -> {
                addCareItem()
            }

            CareEvent.BackClicked -> {
                viewModelScope.launch {
                    _effect.emit(CareEffect.NavigateBack)
                }
            }
        }
    }

    private fun loadCare(
        userId: String,
        date: String
    ) {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    userId = null
                )
            }

            try {
                val user = getCurrentUserUseCase()

                if (user?.id != userId) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Sesión no válida"
                        )
                    }
                    return@launch
                }

                val items = getDailyCareUseCase(
                    userId,
                    date
                )

                // Reutilizamos el plan existente.
                val currentTreatment = try {
                    getTreatmentPlanUseCase(userId)
                        .firstOrNull {
                            it.status ==
                                    TreatmentStepStatus.IN_PROGRESS
                        }?.title
                } catch (e: Exception) {
                    println("CARE_PLAN_ERROR: ${e.message}")
                    null
                }

                // Solo citas agendadas desde la fecha actual.
                val nextAppointment = try {

                    getAppointmentsUseCase(userId)
                        .filter {
                            it.status == AppointmentStatus.SCHEDULED &&
                                    it.date >= date
                        }
                        .minWithOrNull(
                            compareBy<Appointment> { it.date }
                                .thenBy { it.time }
                        )

                } catch (e: Exception) {
                    println("CARE_APPOINTMENT_ERROR: ${e.message}")
                    null
                }

                _state.update {
                    it.copy(
                        userId = userId,
                        date = date,
                        items = items,
                        currentTreatment = currentTreatment,
                        nextAppointment = nextAppointment,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                println("CARE_LOAD_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "No se pudieron cargar los cuidados"
                    )
                }
            }
        }
    }

    private fun addCareItem() {
        val current = state.value
        val userId = current.userId ?: return

        if (current.isSaving || current.isLoading) return

        _state.update {
            it.copy(
                isSaving = true,
                error = null,
                message = null
            )
        }

        viewModelScope.launch {
            try {
                saveCareItemUseCase(
                    userId = userId,
                    title = current.newTitle,
                    note = current.newNote
                )

                val updatedItems = getDailyCareUseCase(
                    userId,
                    current.date
                )

                _state.update {
                    it.copy(
                        items = updatedItems,
                        newTitle = "",
                        newNote = "",
                        isSaving = false,
                        message = "Indicación registrada"
                    )
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSaving = false,
                        error = e.message
                            ?: "No se pudo guardar"
                    )
                }
            }
        }
    }

    private fun changeCompleted(
        careId: Int,
        completed: Boolean
    ) {
        val current = state.value
        val userId = current.userId ?: return

        if (current.isSaving || current.isLoading) return

        _state.update {
            it.copy(
                isSaving = true,
                error = null,
                message = null
            )
        }

        viewModelScope.launch {
            try {
                setCareCompletedUseCase(
                    userId = userId,
                    careId = careId,
                    date = current.date,
                    completed = completed
                )

                val updatedItems = getDailyCareUseCase(
                    userId,
                    current.date
                )

                _state.update {
                    it.copy(
                        items = updatedItems,
                        isSaving = false
                    )
                }

            } catch (e: Exception) {
                println("CARE_CHECK_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isSaving = false,
                        error = "No se pudo actualizar el cumplimiento"
                    )
                }
            }
        }
    }
}