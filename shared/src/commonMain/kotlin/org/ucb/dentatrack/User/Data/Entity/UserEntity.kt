package org.ucb.dentatrack.User.Data.Entity


import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(
            value = ["email"],
            unique = true
        )
    ]
)
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val fullName: String,
    val email: String,
    val password: String,
    val isLoggedIn:Boolean=false
)