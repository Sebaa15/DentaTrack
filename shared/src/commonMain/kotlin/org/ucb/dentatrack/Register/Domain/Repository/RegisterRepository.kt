package org.ucb.dentatrack.Register.Domain.Repository

interface RegisterRepository {

    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Boolean
}