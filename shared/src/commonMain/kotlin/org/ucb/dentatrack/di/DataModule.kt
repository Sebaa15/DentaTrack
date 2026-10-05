package org.ucb.dentatrack.di

import org.koin.dsl.module
import org.ucb.dentatrack.ExchangeRate.Data.datasource.ExchangeRateLocalDataSource
import org.ucb.dentatrack.ExchangeRate.Data.datasource.ExchangeRateLocalDataSourceImpl
import org.ucb.dentatrack.ExchangeRate.Data.repository.ExchangeRateRepositoryImpl
import org.ucb.dentatrack.ExchangeRate.Domain.repository.ExchangeRateRepository
import org.ucb.dentatrack.Login.Data.DataSource.LoginRemoteDataSource
import org.ucb.dentatrack.Login.Data.Repository.LoginRepositoryImpl
import org.ucb.dentatrack.Login.Domain.Repository.LoginRepository
import org.ucb.dentatrack.Register.Data.Repository.FakeRegisterRepository
import org.ucb.dentatrack.Register.Domain.Repository.RegisterRepository
import org.ucb.dentatrack.Login.Data.Service.LoginApiService
import org.ucb.dentatrack.Odontogram.Data.Repository.FakeOdontogramRepository
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository
import org.ucb.dentatrack.database.AppDatabase

val dataModule = module {
    single<LoginRemoteDataSource> {
        LoginApiService()
    }
    single<LoginRepository>{
        LoginRepositoryImpl(get())
    }
    single<RegisterRepository>{
        FakeRegisterRepository()
    }
    single<OdontogramRepository>{
        FakeOdontogramRepository()
    }
    single {
        get<AppDatabase>()
            .exchangeRateDao()
    }
    single<ExchangeRateLocalDataSource> {
        ExchangeRateLocalDataSourceImpl(get())
    }

    single<ExchangeRateRepository> {
        ExchangeRateRepositoryImpl(get())
    }
}