package org.ucb.dentatrack.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module
import org.ucb.dentatrack.scheduler.OdontogramCheckScheduler
import org.ucb.dentatrack.sheduler.OdontogramWorkScheduler

fun initKoinAndroid(
    context: Context
) {

    initKoin {

        androidContext(context)

        androidLogger()

        modules(
            androidDatabaseModule,
            androidFirebaseModule,
            androidGrpcModule,
            androidWorkModule
        )
    }
}
val androidWorkModule = module {
    single<OdontogramWorkScheduler> {
        OdontogramCheckScheduler(get())
    }
}
