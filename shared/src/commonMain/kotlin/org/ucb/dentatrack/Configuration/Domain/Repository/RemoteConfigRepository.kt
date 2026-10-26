package org.ucb.dentatrack.Configuration.Domain.Repository

import kotlinx.coroutines.flow.Flow
import org.ucb.dentatrack.Configuration.Domain.Model.AppRemoteConfig

interface RemoteConfigRepository {

    fun observeConfig():
            Flow<AppRemoteConfig>
}