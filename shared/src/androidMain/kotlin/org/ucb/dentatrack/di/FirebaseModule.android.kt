package org.ucb.dentatrack.di

import org.koin.dsl.module
import org.ucb.dentatrack.Configuration.Data.DataSource.RemoteConfigDataSource
import org.ucb.dentatrack.Configuration.Data.DataSource.RemoteConfigDataSourceImpl

val androidFirebaseModule =
    module {

        single<RemoteConfigDataSource> {
            RemoteConfigDataSourceImpl()
        }
    }