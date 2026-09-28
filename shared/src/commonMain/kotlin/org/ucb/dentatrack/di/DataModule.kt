package org.ucb.dentatrack.di

import org.ucb.dentatrack.Movies.data.datasource.CatalogRemoteDataSource
import org.ucb.dentatrack.Movies.data.repository.CatalogRepositoryImpl
import org.ucb.dentatrack.Movies.data.service.CatalogService
import org.ucb.dentatrack.Movies.domain.repository.CatalogRepository
import org.koin.dsl.module
import org.ucb.dentatrack.Crypto.data.dataSource.CryptoRemoteDataSource
import org.ucb.dentatrack.Crypto.data.repository.CryptoRepositoryImpl
import org.ucb.dentatrack.Crypto.data.service.CryptoService
import org.ucb.dentatrack.Crypto.domain.repository.CryptoRepository
import org.ucb.dentatrack.Login.Data.DataSource.AuthRemoteDataSource
import org.ucb.dentatrack.Login.Domain.Repository.AuthRepository
import org.ucb.dentatrack.Register.Data.Repository.FakeRegisterRepository
import org.ucb.dentatrack.Register.Domain.Repository.RegisterRepository
import org.ucb.dentatrack.Login.Data.Repository.FakeAuthRepository
import org.ucb.dentatrack.Login.Data.Service.DentaTrackApiService
import org.ucb.dentatrack.Odontogram.Data.Repository.FakeOdontogramRepository
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository

val dataModule = module {
    single<AuthRemoteDataSource> {
        DentaTrackApiService()
    }
    single<AuthRepository>{
        FakeAuthRepository()
    }
    single<RegisterRepository>{
        FakeRegisterRepository()
    }
    single<OdontogramRepository>{
        FakeOdontogramRepository()
    }
    single<CatalogRemoteDataSource> {
        CatalogService()
    }
    single<CatalogRepository> {
        CatalogRepositoryImpl(get())
    }
    single<CryptoRemoteDataSource>{
        CryptoService()
    }
    single<CryptoRepository>{
        CryptoRepositoryImpl(get())
    }
}