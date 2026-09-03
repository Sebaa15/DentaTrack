package org.ucb.dentatrack.Login.presentation.State

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.ucb.dentatrack.Login.Domain.Model.LoginError
import org.ucb.dentatrack.Login.Domain.Model.LoginResult
import org.ucb.dentatrack.Login.Domain.UseCase.LoginUseCase
import org.ucb.dentatrack.feature.login.presentation.state.LoginState

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())

    val state: StateFlow<LoginState> =
        _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>(
        extraBufferCapacity = 1
    )

    val effect: SharedFlow<LoginEffect> =
        _effect.asSharedFlow()

    fun onIntent(intent: LoginIntent) {

        when (intent) {

            is LoginIntent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = intent.email,
                        error = null
                    )
                }
            }

            is LoginIntent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = intent.password,
                        error = null
                    )
                }
            }

            LoginIntent.LoginClicked -> {
                login()
            }
        }
    }

    private fun login() {

        val currentState = _state.value

        val result = loginUseCase(
            emailValue = currentState.email,
            passwordValue = currentState.password
        )

        when (result) {

            LoginResult.Success -> {

                _state.update {
                    it.copy(
                        error = null
                    )
                }

                _effect.tryEmit(
                    LoginEffect.NavigateToOdontogram
                )
            }

            is LoginResult.Error -> {

                val message = when (result.type) {

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

                _state.update {
                    it.copy(
                        error = message
                    )
                }
            }
        }
    }
}