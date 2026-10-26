package org.ucb.dentatrack.Login.Domain.UseCase

import org.ucb.dentatrack.Login.Domain.Repository.LoginRepository

class LogoutUseCase(
    private val repository:
    LoginRepository
) {

    suspend operator fun invoke() {
        repository.logout()
    }
}