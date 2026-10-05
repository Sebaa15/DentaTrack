package org.ucb.dentatrack.Login.Data.Dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import org.ucb.dentatrack.Login.Data.Entity.LoggedUserEntity

@Dao
interface LoggedUserDao {

    @Upsert
    suspend fun saveUser(
        user: LoggedUserEntity
    )

    @Query(
        "SELECT * FROM logged_user LIMIT 1"
    )
    suspend fun getLoggedUser():
            LoggedUserEntity?

    @Query(
        "DELETE FROM logged_user"
    )
    suspend fun clearUser()
}