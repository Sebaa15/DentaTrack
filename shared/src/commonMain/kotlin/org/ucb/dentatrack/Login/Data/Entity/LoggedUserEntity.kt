package org.ucb.dentatrack.Login.Data.Entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(
    tableName = "logged_user"
)
data class LoggedUserEntity(

    @PrimaryKey
    val localId: Int = 1,

    val userId: String,

    val fullName: String,

    val email: String
)