package org.ucb.dentatrack.Login.Data.Mapper

import org.ucb.dentatrack.Login.Data.Entity.LoggedUserEntity
import org.ucb.dentatrack.Login.Domain.Model.User

fun User.toEntity():
        LoggedUserEntity {

    return LoggedUserEntity(
        userId = id,
        fullName = fullName,
        email = email
    )
}

fun LoggedUserEntity.toDomain():
        User {

    return User(
        id = userId,
        fullName = fullName,
        email = email
    )
}