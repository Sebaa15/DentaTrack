package org.ucb.dentatrack.di

import org.koin.dsl.module
import org.ucb.dentatrack.sheduler.OdontogramWorkScheduler

fun initKoinIos() {

    initKoin {

        modules(
            iosDatabaseModule,
            iosWorkModule
        )
    }
}
val iosWorkModule = module {
    single<OdontogramWorkScheduler> {
        object : OdontogramWorkScheduler {
            override fun schedule(userId: String) {}
            override fun cancel() {}
        }
    }
}