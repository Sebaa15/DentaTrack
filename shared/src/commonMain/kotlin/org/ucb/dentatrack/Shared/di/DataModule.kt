package org.ucb.dentatrack.Shared.di

import org.koin.dsl.module
import org.ucb.dentatrack.Login.Data.Repository.FakeAuthRepository
import org.ucb.dentatrack.Login.Domain.Repository.AuthRepository
import org.ucb.dentatrack.Register.Data.Repository.FakeRegisterRepository
import org.ucb.dentatrack.Register.Domain.Repository.RegisterRepository

val dataModule = module {

    single<AuthRepository> {
        FakeAuthRepository()
    }

    single<RegisterRepository> {
        FakeRegisterRepository()
    }
}