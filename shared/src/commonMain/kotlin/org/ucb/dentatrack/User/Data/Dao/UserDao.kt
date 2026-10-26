package org.ucb.dentatrack.User.Data.Dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import org.ucb.dentatrack.User.Data.Entity.UserEntity

@Dao
interface UserDao {

    @Query("UPDATE users SET isLoggedIn = 0")
    suspend fun clearSession()

    @Query(
        "UPDATE users SET isLoggedIn = 1 WHERE id = :userId"
    )
    suspend fun setLoggedIn(
        userId: Int
    )

    @Query(
        "SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1"
    )
    suspend fun getCurrentUser(): UserEntity?
    @Query(
        """
        SELECT * FROM users
        WHERE email = :email
        AND password = :password
        LIMIT 1
        """
    )
    suspend fun login(
        email: String,
        password: String
    ): UserEntity?

    @Query(
        """
        SELECT * FROM users
        WHERE email = :email
        LIMIT 1
        """
    )
    suspend fun findByEmail(
        email: String
    ): UserEntity?

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun insertUser(
        user: UserEntity
    ): Long
}