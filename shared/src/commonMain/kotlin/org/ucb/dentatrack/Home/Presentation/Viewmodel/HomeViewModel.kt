package org.ucb.dentatrack.Home.Presentation.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.dentatrack.Login.Domain.UseCase.GetCurrentUserUseCase
import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStepStatus
import org.ucb.dentatrack.TreatmentPlan.Domain.UseCase.GetTreatmentPlanUseCase

class HomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getTreatmentPlanUseCase: GetTreatmentPlanUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    fun emitEvent(event: HomeEvent) {
        when (event) {

            is HomeEvent.Start -> {
                loadUser(event.userId)
            }

            HomeEvent.OdontogramClicked -> {
                openOdontogram()
            }
            HomeEvent.PlanClicked -> {

                val userId = state.value.userId

                if (userId != null) {
                    viewModelScope.launch {
                        _effect.emit(
                            HomeEffect.NavigateToPlan(userId)
                        )
                    }
                }
            }
        }
    }

    private fun loadUser(userId: String) {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    planError = null,
                    totalSteps = 0,
                    completedSteps = 0
                )
            }

            try {
                val user = getCurrentUserUseCase()

                if (user == null || user.id != userId) {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Sesión no disponible"
                        )
                    }

                    _effect.emit(
                        HomeEffect.NavigateToLogin
                    )

                    return@launch
                }

                var total = 0
                var completed = 0
                var planError: String? = null

                try {
                    val steps = getTreatmentPlanUseCase(userId)

                    total = steps.size

                    completed = steps.count {
                        it.status == TreatmentStepStatus.COMPLETED
                    }

                } catch (e: Exception) {
                    println("HOME_PLAN_ERROR: ${e.message}")
                    planError = "No se pudo cargar el progreso"
                }

                _state.update {
                    it.copy(
                        userId = user.id,
                        userName = user.fullName,
                        totalSteps = total,
                        completedSteps = completed,
                        planError = planError,
                        isLoading = false,
                        error = null
                    )
                }

            } catch (e: Exception) {

                println("HOME_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar el usuario"
                    )
                }
            }
        }
    }

    private fun openOdontogram() {
        val userId = state.value.userId ?: return

        viewModelScope.launch {
            _effect.emit(
                HomeEffect.NavigateToOdontogram(userId)
            )
        }
    }
}