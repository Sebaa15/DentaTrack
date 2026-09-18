package org.ucb.dentatrack.Login.Data.Mapper

import org.ucb.dentatrack.Login.Data.Dto.LoginResponseDto
import org.ucb.dentatrack.Login.Domain.Model.User

fun LoginResponseDto.Domain(): User{
    return User(
        id=id,
        fullName=fullName,
        email=email
    )
}