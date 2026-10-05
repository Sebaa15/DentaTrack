package org.ucb.dentatrack.Register.Data.Repository

import org.ucb.dentatrack.Register.Domain.Repository.RegisterRepository
import org.ucb.dentatrack.User.Data.DataSource.UserLocalDataSource

class RegisterRepositoryImpl(
    private val localDataSource:
    UserLocalDataSource
) : RegisterRepository {

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Boolean {

        return localDataSource.register(
            fullName = fullName,
            email = email,
            password = password
        )
    }
}