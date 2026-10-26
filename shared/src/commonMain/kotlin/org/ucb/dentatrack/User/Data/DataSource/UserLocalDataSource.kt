package org.ucb.dentatrack.User.Data.DataSource

import org.ucb.dentatrack.User.Data.Entity.UserEntity

interface UserLocalDataSource {

    suspend fun login(
        email: String,
        password: String
    ): UserEntity?

    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Boolean
    suspend fun setLoggedIn(userId: Int)

    suspend fun getCurrentUser(): UserEntity?

    suspend fun logout()
}