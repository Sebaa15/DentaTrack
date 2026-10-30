package org.ucb.dentatrack.TreatmentDetail.Presentation.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import org.ucb.dentatrack.Login.Domain.UseCase.GetCurrentUserUseCase
import org.ucb.dentatrack.Odontogram.Domain.UseCase.GetOdontogramUseCase

class TreatmentDetailViewModel(
    private val getOdontogramUseCase: GetOdontogramUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow(TreatmentDetailState())

    val state = _state.asStateFlow()

    private val _effect =
        MutableSharedFlow<TreatmentDetailEffect>()

    val effect = _effect.asSharedFlow()

    fun emitEvent(event: TreatmentDetailEvent) {
        when (event) {

            is TreatmentDetailEvent.Start -> {
                loadTooth(
                    userId = event.userId,
                    toothNumber = event.toothNumber
                )
            }

            TreatmentDetailEvent.BackClicked -> {
                viewModelScope.launch {
                    _effect.emit(
                        TreatmentDetailEffect.NavigateBack
                    )
                }
            }
        }
    }

    private fun loadTooth(
        userId: String,
        toothNumber: Int
    ) {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    toothNumber = toothNumber,
                    tooth = null,
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

                val teeth = getOdontogramUseCase(userId)

                val selectedTooth = teeth.find {
                    it.number == toothNumber
                }

                _state.update {
                    it.copy(
                        tooth = selectedTooth,
                        isLoading = false,
                        error = if (selectedTooth == null)
                            "Esta pieza aún no tiene información registrada"
                        else
                            null
                    )
                }

            } catch (e: Exception) {

                println(
                    "TREATMENT_DETAIL_ERROR: ${e.message}"
                )

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "No se pudo cargar la pieza dental"
                    )
                }
            }
        }
    }
}