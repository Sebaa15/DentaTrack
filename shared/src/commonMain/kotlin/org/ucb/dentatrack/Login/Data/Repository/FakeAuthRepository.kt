package org.ucb.dentatrack.Login.Data.Repository

import org.ucb.dentatrack.Login.Domain.Repository.AuthRepository
import org.ucb.dentatrack.Login.Domain.Vo.Email
import org.ucb.dentatrack.Login.Domain.Vo.Password

class FakeAuthRepository : AuthRepository {

    override fun login(
        email: Email,
        password: Password
    ): Boolean {

        return email.value == "paciente@dentatrack.com" &&
                password.value == "123456"
    }
}