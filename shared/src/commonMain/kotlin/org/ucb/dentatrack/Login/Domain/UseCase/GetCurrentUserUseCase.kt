package org.ucb.dentatrack.Login.Domain.UseCase


import org.ucb.dentatrack.Login.Domain.Model.User
import org.ucb.dentatrack.Login.Domain.Repository.LoginRepository

class GetCurrentUserUseCase(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(): User? {
        return repository.getCurrentUser()
    }
}