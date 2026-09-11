package org.ucb.dentatrack.Register.Presentation.State

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.ucb.dentatrack.Register.Domain.Model.RegisterError
import org.ucb.dentatrack.Register.Domain.Model.RegisterResult
import org.ucb.dentatrack.Register.Domain.UseCase.RegisterUseCase
import org.ucb.dentatrack.Register.Presentation.State.RegisterState

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        RegisterState()
    )

    val state: StateFlow<RegisterState> =
        _state.asStateFlow()

    fun onFullNameChanged(value: String) {
        _state.update {
            it.copy(
                fullName = value,
                error = null
            )
        }
    }

    fun onEmailChanged(value: String) {
        _state.update {
            it.copy(
                email = value,
                error = null
            )
        }
    }

    fun onPasswordChanged(value: String) {
        _state.update {
            it.copy(
                password = value,
                error = null
            )
        }
    }

    fun onConfirmPasswordChanged(value: String) {
        _state.update {
            it.copy(
                confirmPassword = value,
                error = null
            )
        }
    }

    fun register() {

        val currentState = _state.value

        val result = registerUseCase(
            fullName = currentState.fullName,
            email = currentState.email,
            password = currentState.password,
            confirmPassword = currentState.confirmPassword
        )

        when (result) {

            RegisterResult.Success -> {

                _state.update {
                    it.copy(
                        error = null,
                        isRegistered = true
                    )
                }
            }

            is RegisterResult.Error -> {

                val message = when (result.type) {

                    RegisterError.EMPTY_NAME ->
                        "Ingrese su nombre completo"

                    RegisterError.EMPTY_EMAIL ->
                        "Ingrese su correo electrónico"

                    RegisterError.INVALID_EMAIL ->
                        "El correo electrónico no es válido"

                    RegisterError.EMPTY_PASSWORD ->
                        "Ingrese una contraseña"

                    RegisterError.SHORT_PASSWORD ->
                        "La contraseña debe tener al menos 6 caracteres"

                    RegisterError.PASSWORDS_DO_NOT_MATCH ->
                        "Las contraseñas no coinciden"

                    RegisterError.USER_ALREADY_EXISTS ->
                        "El correo ya se encuentra registrado"
                }

                _state.update {
                    it.copy(
                        error = message,
                        isRegistered = false
                    )
                }
            }
        }
    }
}