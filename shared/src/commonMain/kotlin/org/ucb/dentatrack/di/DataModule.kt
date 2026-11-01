package org.ucb.dentatrack.di

import org.koin.dsl.module
import org.ucb.dentatrack.Configuration.Data.Repository.RemoteConfigRepositoryImpl
import org.ucb.dentatrack.Configuration.Domain.Repository.RemoteConfigRepository
import org.ucb.dentatrack.Login.Data.Repository.LoginRepositoryImpl
import org.ucb.dentatrack.Login.Domain.Repository.LoginRepository
import org.ucb.dentatrack.Notifications.Data.DataSource.NotificationFirebaseDataSource
import org.ucb.dentatrack.Notifications.Data.Repository.NotificationRepositoryImpl
import org.ucb.dentatrack.Notifications.Domain.Repository.NotificationRepository
import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramFirestoreDataSource
import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramLocalDataSource
import org.ucb.dentatrack.Odontogram.Data.DataSource.OdontogramLocalDataSourceImpl
import org.ucb.dentatrack.Odontogram.Data.Repository.OdontogramRepositoryImpl
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository
import org.ucb.dentatrack.Register.Data.Repository.RegisterRepositoryImpl
import org.ucb.dentatrack.Register.Domain.Repository.RegisterRepository
import org.ucb.dentatrack.TreatmentPlan.Data.Repository.TreatmentPlanRepositoryImpl
import org.ucb.dentatrack.TreatmentPlan.Domain.Repository.TreatmentPlanRepository
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
            localDataSource = get(),
            firestoreDataSource = get()
        )
    }
    single<RemoteConfigRepository> {
        RemoteConfigRepositoryImpl(
            get()
        )
    }
    single {
        NotificationFirebaseDataSource()
    }

    single<NotificationRepository> {
        NotificationRepositoryImpl(
            get()
        )
    }
    single {
        OdontogramFirestoreDataSource()
    }
    single {
        get<AppDatabase>().treatmentStepDao()
    }

    single<TreatmentPlanRepository> {
        TreatmentPlanRepositoryImpl(get())
    }
}