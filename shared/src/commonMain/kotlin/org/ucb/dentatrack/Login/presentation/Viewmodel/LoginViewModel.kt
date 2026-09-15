package org.ucb.dentatrack.Login.presentation.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.dentatrack.Login.Domain.Model.LoginError
import org.ucb.dentatrack.Login.Domain.Model.LoginResult
import org.ucb.dentatrack.Login.Domain.UseCase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect = _effect.asSharedFlow()

    fun emitEvent(event: LoginEvent) {
        when (event) {

            is LoginEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        error = null
                    )
                }
            }

            is LoginEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        error = null
                    )
                }
            }

            LoginEvent.LoginClicked -> login()
        }
    }

    private fun login() {

        val result = loginUseCase(
            emailValue = state.value.email,
            passwordValue = state.value.password
        )

        when (result) {

            LoginResult.Success -> {
                _state.update {
                    it.copy(error = null)
                }

                emitEffect(
                    LoginEffect.NavigateToOdontogram
                )
            }

            is LoginResult.Error -> {
                _state.update {
                    it.copy(
                        error = getErrorMessage(result.type)
                    )
                }
            }
        }
    }

    private fun emitEffect(effect: LoginEffect) =
        viewModelScope.launch {
            _effect.emit(effect)
        }

    private fun getErrorMessage(
        error: LoginError
    ): String {
        return when (error) {

            LoginError.EMPTY_EMAIL ->
                "Ingrese su correo electrónico"

            LoginError.INVALID_EMAIL ->
                "El correo electrónico no es válido"

            LoginError.EMPTY_PASSWORD ->
                "Ingrese su contraseña"

            LoginError.SHORT_PASSWORD ->
                "La contraseña debe tener al menos 6 caracteres"

            LoginError.INVALID_CREDENTIALS ->
                "Correo o contraseña incorrectos"
        }
    }
}