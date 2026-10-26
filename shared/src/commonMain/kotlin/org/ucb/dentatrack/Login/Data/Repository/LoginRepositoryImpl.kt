package org.ucb.dentatrack.Login.Data.Repository

import org.ucb.dentatrack.Login.Domain.Model.User
import org.ucb.dentatrack.Login.Domain.Repository.LoginRepository
import org.ucb.dentatrack.Login.Domain.Vo.Email
import org.ucb.dentatrack.Login.Domain.Vo.Password
import org.ucb.dentatrack.User.Data.DataSource.UserLocalDataSource
import org.ucb.dentatrack.User.Data.Mapper.toDomain

class LoginRepositoryImpl(
    private val localDataSource:
    UserLocalDataSource
) : LoginRepository {

    override suspend fun login(
        email: Email,
        password: Password
    ): Result<User> {

        val user =
            localDataSource.login(
                email = email.value,
                password = password.value
            )

        return if (user != null) {
            localDataSource.setLoggedIn(
                user.id
            )
            Result.success(
                user.toDomain()
            )

        } else {

            Result.failure(
                Exception(
                    "Credenciales incorrectas"
                )
            )
        }
    }
    override suspend fun logout() {
        localDataSource.logout()
    }
    override suspend fun getCurrentUser():
            User? {

        return localDataSource
            .getCurrentUser()
            ?.toDomain()
    }
}