package org.ucb.dentatrack.di

import org.koin.dsl.module
import org.ucb.dentatrack.Login.Data.DataSource.AuthRemoteDataSource
import org.ucb.dentatrack.Login.Domain.Repository.AuthRepository
import org.ucb.dentatrack.Register.Data.Repository.FakeRegisterRepository
import org.ucb.dentatrack.Register.Domain.Repository.RegisterRepository
import org.ucb.dentatrack.Login.Data.Repository.FakeAuthRepository
import org.ucb.dentatrack.Login.Data.Service.DentaTrackApiService
import org.ucb.dentatrack.Odontogram.Data.Repository.FakeOdontogramRepository
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository
import org.ucb.dentatrack.Odontogram.Presentation.Viewmodel.OdontogramEvent

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
}