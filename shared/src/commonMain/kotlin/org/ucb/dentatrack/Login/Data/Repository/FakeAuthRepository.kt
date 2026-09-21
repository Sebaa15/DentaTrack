package org.ucb.dentatrack.Login.Data.Repository

import org.ucb.dentatrack.Login.Domain.Model.User
import org.ucb.dentatrack.Login.Domain.Repository.AuthRepository
import org.ucb.dentatrack.Login.Domain.Vo.Email
import org.ucb.dentatrack.Login.Domain.Vo.Password

class FakeAuthRepository: AuthRepository{
    override suspend fun login(
        email: Email,
        password: Password
    ): Result<User> {
      return if(
          email.value=="paciente@gmail.com" &&
          password.value=="123456"
      ){
          Result.success(
              User(
                  id="1", fullName ="Paciente DentaTrack",
                  email = email.value
              )
          )
      }else{
          Result.failure(Exception("Credenciales incorrectas"))
      }
    }
}