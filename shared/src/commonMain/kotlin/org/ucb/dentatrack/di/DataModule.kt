package org.ucb.dentatrack.di

import org.koin.dsl.module
import org.ucb.dentatrack.Login.Data.Repository.LoginRepositoryImpl
import org.ucb.dentatrack.Login.Domain.Repository.LoginRepository
import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramLocalDataSource
import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramLocalDataSourceImpl
import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramRealtimeDataSource
import org.ucb.dentatrack.Odontogram.Data.Repository.OdontogramRealtimeRepositoryImpl
import org.ucb.dentatrack.Odontogram.Data.Repository.OdontogramRepositoryImpl
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRealtimeRepository
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository
import org.ucb.dentatrack.Register.Data.Repository.RegisterRepositoryImpl
import org.ucb.dentatrack.Register.Domain.Repository.RegisterRepository
import org.ucb.dentatrack.User.Data.DataSource.UserLocalDataSource
import org.ucb.dentatrack.User.Data.DataSource.UserLocalDataSourceImpl
import org.ucb.dentatrack.database.AppDatabase

val dataModule = module {


    single {
        get<AppDatabase>()
            .userDao()
    }

    single<UserLocalDataSource> {
        UserLocalDataSourceImpl(
            get()
        )
    }

    // LOGIN LOCAL

    single<LoginRepository> {
        LoginRepositoryImpl(
            get()
        )
    }


    single<RegisterRepository> {
        RegisterRepositoryImpl(
            get()
        )
    }


    single {
        get<AppDatabase>()
            .toothDao()
    }

    single<OdontogramLocalDataSource> {
        OdontogramLocalDataSourceImpl(
            get()
        )
    }

    single<OdontogramRepository> {
        OdontogramRepositoryImpl(
            get()
        )
    }
    single {
        OdontogramRealtimeDataSource()
    }

    single<OdontogramRealtimeRepository> {
        OdontogramRealtimeRepositoryImpl(
            get()
        )
    }
}