package org.ucb.dentatrack.Register.Domain.Repository

interface RegisterRepository {

    fun register(
        fullName: String,
        email: String,
        password: String
    ): Boolean
}