package org.ucb.dentatrack.Login.Data.DataSource

import org.ucb.dentatrack.Login.Data.Entity.LoggedUserEntity

interface LoginLocalDataSource {

    suspend fun saveUser(
        user: LoggedUserEntity
    )

    suspend fun getLoggedUser():
            LoggedUserEntity?

    suspend fun clearUser()
}