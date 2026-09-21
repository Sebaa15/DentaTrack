package org.ucb.dentatrack.Login.Domain.UseCase

import org.ucb.dentatrack.Login.Domain.Model.LoginError
import org.ucb.dentatrack.Login.Domain.Model.LoginResult
import org.ucb.dentatrack.Login.Domain.Repository.AuthRepository
import org.ucb.dentatrack.Login.Domain.Vo.Email
import org.ucb.dentatrack.Login.Domain.Vo.Password

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        emailValue: String,
        passwordValue: String
    ): LoginResult {

        val email = Email(
            emailValue.trim()
        )

        val password = Password(
            passwordValue
        )

        if (email.isBlank()) {
            return LoginResult.Error(
                LoginError.EMPTY_EMAIL
            )
        }

        if (!email.isValid()) {
            return LoginResult.Error(
                LoginError.INVALID_EMAIL
            )
        }

        if (password.isBlank()) {
            return LoginResult.Error(
                LoginError.EMPTY_PASSWORD
            )
        }

        if (!password.hasValidLength()) {
            return LoginResult.Error(
                LoginError.SHORT_PASSWORD
            )
        }

        return repository.login(
            email=email,password=password
        ).fold(
            onSuccess = {
                user->LoginResult.Success(user=user)
            },
            onFailure = {
                LoginResult.Error(
                    LoginError.INVALID_CREDENTIALS
                )
            }
        )


    }
}