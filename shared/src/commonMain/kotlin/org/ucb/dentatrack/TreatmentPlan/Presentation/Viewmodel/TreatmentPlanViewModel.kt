package org.ucb.dentatrack.TreatmentPlan.Presentation.Viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import org.ucb.dentatrack.Login.Domain.UseCase.GetCurrentUserUseCase
import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStep
import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStepStatus
import org.ucb.dentatrack.TreatmentPlan.Domain.UseCase.GetTreatmentPlanUseCase
import org.ucb.dentatrack.TreatmentPlan.Domain.UseCase.SaveTreatmentStepUseCase

class TreatmentPlanViewModel(
    private val getTreatmentPlanUseCase: GetTreatmentPlanUseCase,
    private val saveTreatmentStepUseCase: SaveTreatmentStepUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TreatmentPlanState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TreatmentPlanEffect>()
    val effect = _effect.asSharedFlow()

    fun emitEvent(event: TreatmentPlanEvent) {

        when (event) {

            is TreatmentPlanEvent.Start -> {
                loadPlan(event.userId)
            }

            is TreatmentPlanEvent.TitleChanged -> {
                _state.update {
                    it.copy(title = event.value, error = null)
                }
            }

            is TreatmentPlanEvent.DateChanged -> {
                _state.update {
                    it.copy(date = event.value)
                }
            }

            is TreatmentPlanEvent.NoteChanged -> {
                _state.update {
                    it.copy(note = event.value)
                }
            }

            is TreatmentPlanEvent.StatusChanged -> {
                _state.update {
                    it.copy(editingStatus = event.status)
                }
            }

            is TreatmentPlanEvent.EditClicked -> {
                if (!state.value.isSaving) {
                    val step = state.value.steps.find {
                        it.id == event.stepId
                    }

                    if (step != null) {
                        _state.update {
                            it.copy(
                                editingId = step.id,
                                title = step.title,
                                date = step.date,
                                note = step.note,
                                editingStatus = step.status,
                                message = null,
                                error = null
                            )
                        }
                    }
                }
            }

            TreatmentPlanEvent.NewClicked -> {
                if (!state.value.isSaving) {
                    clearForm()
                }
            }

            TreatmentPlanEvent.SaveClicked -> {
                saveStep()
            }

            TreatmentPlanEvent.BackClicked -> {
                viewModelScope.launch {
                    _effect.emit(
                        TreatmentPlanEffect.NavigateBack
                    )
                }
            }
        }
    }

    private fun loadPlan(userId: String) {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val currentUser = getCurrentUserUseCase()

                if (currentUser?.id != userId) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Sesión no válida"
                        )
                    }
                    return@launch
                }

                val steps = getTreatmentPlanUseCase(userId)

                _state.update {
                    it.copy(
                        userId = userId,
                        steps = steps,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                println("PLAN_LOAD_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar el plan"
                    )
                }
            }
        }
    }

    private fun saveStep() {

        val current = state.value
        val userId = current.userId ?: return

        if (current.isSaving || current.isLoading) return

        if (current.title.isBlank()) {
            _state.update {
                it.copy(error = "Ingrese el nombre de la etapa")
            }
            return
        }

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isSaving = true,
                    error = null,
                    message = null
                )
            }

            try {
                saveTreatmentStepUseCase(
                    userId = userId,
                    step = TreatmentStep(
                        id = current.editingId,
                        title = current.title,
                        date = current.date,
                        note = current.note,
                        status = current.editingStatus
                    )
                )

                val updatedSteps =
                    getTreatmentPlanUseCase(userId)

                _state.update {
                    it.copy(
                        steps = updatedSteps,
                        isSaving = false,
                        editingId = 0,
                        title = "",
                        date = "",
                        note = "",
                        editingStatus =
                            TreatmentStepStatus.PENDING,
                        message = "Etapa guardada correctamente"
                    )
                }

            } catch (e: Exception) {
                println("PLAN_SAVE_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isSaving = false,
                        error = "Error al guardar la etapa"
                    )
                }
            }
        }
    }

    private fun clearForm() {
        _state.update {
            it.copy(
                editingId = 0,
                title = "",
                date = "",
                note = "",
                editingStatus = TreatmentStepStatus.PENDING,
                error = null,
                message = null
            )
        }
    }
}