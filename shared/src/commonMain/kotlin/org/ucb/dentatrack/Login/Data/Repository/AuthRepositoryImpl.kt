package org.ucb.dentatrack.Login.Data.Repository

import org.ucb.dentatrack.Login.Data.DataSource.AuthRemoteDataSource
import org.ucb.dentatrack.Login.Data.Dto.LoginRequestDto
import org.ucb.dentatrack.Login.Data.Mapper.toDomain
import org.ucb.dentatrack.Login.Domain.Model.User
import org.ucb.dentatrack.Login.Domain.Repository.AuthRepository
import org.ucb.dentatrack.Login.Domain.Vo.Email
import org.ucb.dentatrack.Login.Domain.Vo.Password

class AuthRepositoryImpl (
    private val dataSource: AuthRemoteDataSource
): AuthRepository {
    override suspend fun login(
        email: Email,
        password: Password
    ): Result<User>{
        return try{
            val request= LoginRequestDto(email = email.value, password = password.value)
            val response=dataSource.login(request)
            Result.success(
                response.toDomain()
            )
        }catch(e:Exception){
            Result.failure(e)
        }
    }
}