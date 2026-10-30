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

class HomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
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
        }
    }

    private fun loadUser(userId: String) {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
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

                _state.update {
                    it.copy(
                        userId = user.id,
                        userName = user.fullName,
                        isLoading = false,
                        error = null
                    )
                }

            } catch (e: Exception) {
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