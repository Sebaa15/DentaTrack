package org.ucb.dentatrack.Configuration.Data.Repository

import kotlinx.coroutines.flow.Flow
import org.ucb.dentatrack.Configuration.Data.DataSource.RemoteConfigDataSource
import org.ucb.dentatrack.Configuration.Domain.Model.AppRemoteConfig
import org.ucb.dentatrack.Configuration.Domain.Repository.RemoteConfigRepository

class RemoteConfigRepositoryImpl(
    private val dataSource:
    RemoteConfigDataSource
) : RemoteConfigRepository {

    override fun observeConfig():
            Flow<AppRemoteConfig> {

        return dataSource
            .observeConfig()
    }
}