package org.ucb.dentatrack.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.ucb.dentatrack.database.AppDatabase
import org.ucb.dentatrack.database.getDatabaseBuilder
import org.ucb.dentatrack.database.getRoomDatabase

val androidDatabaseModule =
    module {

        single<AppDatabase> {

            getRoomDatabase(
                getDatabaseBuilder(
                    androidContext()
                )
            )
        }
    }