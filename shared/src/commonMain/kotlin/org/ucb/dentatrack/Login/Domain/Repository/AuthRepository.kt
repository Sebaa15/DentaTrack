package org.ucb.dentatrack.Login.Domain.Repository

import org.ucb.dentatrack.Login.Domain.Vo.Email
import org.ucb.dentatrack.Login.Domain.Vo.Password

interface AuthRepository {

    fun login(
        email: Email,
        password: Password
    ): Boolean
}