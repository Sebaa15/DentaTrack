package org.ucb.dentatrack.Login.Domain.Repository

import org.ucb.dentatrack.Login.Domain.Vo.Email
import org.ucb.dentatrack.Login.Domain.Vo.Password
import org.ucb.dentatrack.Login.Domain.Model.User

interface AuthRepository {

    suspend fun login(
        email: Email,
        password: Password
    ): Result<User>
}