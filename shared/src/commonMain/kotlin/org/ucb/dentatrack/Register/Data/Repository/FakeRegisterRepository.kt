package org.ucb.dentatrack.Register.Data.Repository

import org.ucb.dentatrack.Register.Domain.Repository.RegisterRepository

class FakeRegisterRepository : RegisterRepository {

    override fun register(
        fullName: String,
        email: String,
        password: String
    ): Boolean {

        return email != "paciente@dentatrack.com"
    }
}