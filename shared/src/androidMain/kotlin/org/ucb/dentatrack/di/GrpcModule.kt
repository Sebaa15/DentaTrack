package org.ucb.dentatrack.di

import kotlinx.coroutines.launch
import com.develoop.logs.LogServiceClient
import com.squareup.wire.GrpcClient
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.dsl.module

import org.ucb.dentatrack.Logs.Data.LogsRemoteDataSource
import org.ucb.dentatrack.Logs.Data.LogsRepositoryImpl
import org.ucb.dentatrack.Logs.Domain.Repository.LogsRepository

val androidGrpcModule = module {

    single {
        val client = OkHttpClient.Builder()
            .protocols(
                listOf(
                    Protocol.HTTP_2,
                    Protocol.HTTP_1_1
                )
            )
            .build()

        GrpcClient.Builder()
            .client(client)
            .baseUrl("https://8a69-192-223-121-195.ngrok-free.app")
            .build()
            .create(LogServiceClient::class)
    }

    single {
        LogsRemoteDataSource(get())
    }

    single<LogsRepository> {
        LogsRepositoryImpl(get())
    }
}
