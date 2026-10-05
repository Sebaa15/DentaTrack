package org.ucb.dentatrack.User.Data.DataSource


import org.ucb.dentatrack.User.Data.Dao.UserDao
import org.ucb.dentatrack.User.Data.Entity.UserEntity

class UserLocalDataSourceImpl(
    private val dao: UserDao
) : UserLocalDataSource {

    override suspend fun login(
        email: String,
        password: String
    ): UserEntity? {

        return dao.login(
            email = email,
            password = password
        )
    }

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Boolean {

        val result =
            dao.insertUser(
                UserEntity(
                    fullName = fullName,
                    email = email,
                    password = password
                )
            )

        return result != -1L
    }
}