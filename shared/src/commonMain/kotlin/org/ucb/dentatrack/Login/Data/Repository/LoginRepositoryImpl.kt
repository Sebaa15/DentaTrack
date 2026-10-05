package org.ucb.dentatrack.Login.Data.Repository

import org.ucb.dentatrack.Login.Data.DataSource.LoginRemoteDataSource
import org.ucb.dentatrack.Login.Data.Dto.LoginRequestDto
import org.ucb.dentatrack.Login.Data.Mapper.toDomain
import org.ucb.dentatrack.Login.Domain.Model.User
import org.ucb.dentatrack.Login.Domain.Repository.LoginRepository
import org.ucb.dentatrack.Login.Domain.Vo.Email
import org.ucb.dentatrack.Login.Domain.Vo.Password

class LoginRepositoryImpl (
    private val dataSource: LoginRemoteDataSource
): LoginRepository {
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
            println("Login error:${e.message}")
            Result.failure(e)
        }
    }
}