package org.ucb.dentatrack.Login.Domain.Repository

import org.ucb.dentatrack.Login.Domain.Model.User
import org.ucb.dentatrack.Login.Domain.Vo.Email
import org.ucb.dentatrack.Login.Domain.Vo.Password

interface LoginRepository {
    suspend fun login(
        email: Email,
        password: Password
    ): Result<User>
}