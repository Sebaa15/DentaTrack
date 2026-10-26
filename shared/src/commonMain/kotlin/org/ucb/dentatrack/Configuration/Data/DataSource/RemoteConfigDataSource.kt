package org.ucb.dentatrack.Configuration.Data.DataSource

import kotlinx.coroutines.flow.Flow
import org.ucb.dentatrack.Configuration.Domain.Model.AppRemoteConfig

interface RemoteConfigDataSource {

    fun observeConfig():
            Flow<AppRemoteConfig>
}