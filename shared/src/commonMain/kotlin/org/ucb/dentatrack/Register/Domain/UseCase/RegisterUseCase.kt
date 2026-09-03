package org.ucb.dentatrack.Register.Domain.UseCase

import org.ucb.dentatrack.Register.Domain.Model.RegisterError
import org.ucb.dentatrack.Register.Domain.Model.RegisterResult
import org.ucb.dentatrack.Register.Domain.Repository.RegisterRepository

class RegisterUseCase(
    private val repository: RegisterRepository
) {

    operator fun invoke(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): RegisterResult {

        if (fullName.isBlank()) {
            return RegisterResult.Error(
                RegisterError.EMPTY_NAME
            )
        }

        if (email.isBlank()) {
            return RegisterResult.Error(
                RegisterError.EMPTY_EMAIL
            )
        }

        if (!email.contains("@")) {
            return RegisterResult.Error(
                RegisterError.INVALID_EMAIL
            )
        }

        if (password.isBlank()) {
            return RegisterResult.Error(
                RegisterError.EMPTY_PASSWORD
            )
        }

        if (password.length < 6) {
            return RegisterResult.Error(
                RegisterError.SHORT_PASSWORD
            )
        }

        if (password != confirmPassword) {
            return RegisterResult.Error(
                RegisterError.PASSWORDS_DO_NOT_MATCH
            )
        }

        val success = repository.register(
            fullName = fullName,
            email = email,
            password = password
        )

        return if (success) {
            RegisterResult.Success
        } else {
            RegisterResult.Error(
                RegisterError.USER_ALREADY_EXISTS
            )
        }
    }
}