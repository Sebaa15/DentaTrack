package org.ucb.dentatrack.Login.Data.DataSource

import org.ucb.dentatrack.Login.Data.Dto.LoginRequestDto
import org.ucb.dentatrack.Login.Data.Dto.LoginResponseDto

interface LoginRemoteDataSource {

    suspend fun login(
        request: LoginRequestDto
    ): LoginResponseDto
}