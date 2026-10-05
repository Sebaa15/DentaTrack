package org.ucb.dentatrack.Login.Data.DataSource

import org.ucb.dentatrack.Login.Data.Dao.LoggedUserDao
import org.ucb.dentatrack.Login.Data.Entity.LoggedUserEntity

class LoginLocalDataSourceImpl(
    private val dao: LoggedUserDao
) : LoginLocalDataSource {

    override suspend fun saveUser(
        user: LoggedUserEntity
    ) {
        dao.saveUser(user)
    }

    override suspend fun getLoggedUser():
            LoggedUserEntity? {

        return dao.getLoggedUser()
    }

    override suspend fun clearUser() {
        dao.clearUser()
    }
}