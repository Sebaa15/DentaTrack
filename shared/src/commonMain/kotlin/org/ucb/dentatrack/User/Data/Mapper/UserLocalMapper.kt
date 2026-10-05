package org.ucb.dentatrack.User.Data.Mapper

import org.ucb.dentatrack.Login.Domain.Model.User
import org.ucb.dentatrack.User.Data.Entity.UserEntity

fun UserEntity.toDomain(): User {

    return User(
        id = id.toString(),
        fullName = fullName,
        email = email
    )
}