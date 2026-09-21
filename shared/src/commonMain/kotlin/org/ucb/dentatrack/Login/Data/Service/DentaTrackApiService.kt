package org.ucb.dentatrack.Login.Data.Service

import io.ktor.client.HttpClient
import org.ucb.dentatrack.Login.Data.DataSource.AuthRemoteDataSource
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.ucb.dentatrack.Login.Data.Dto.LoginRequestDto
import org.ucb.dentatrack.Login.Data.Dto.LoginResponseDto


class DentaTrackApiService: AuthRemoteDataSource{
    private  val client= HttpClient{
        install(ContentNegotiation){
            json(
                Json{
                    prettyPrint=true
                    isLenient=true
                    ignoreUnknownKeys=true
                }
            )
        }
    }
    override suspend fun login(
        request: LoginRequestDto
    ): LoginResponseDto{
        return client.post(
            "$BASE_URL/login"
        ){
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
    companion object{
        const val BASE_URL="http://10.0.2.2:8080"
    }
}