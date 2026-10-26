package org.ucb.dentatrack.Configuration.Domain.UseCase

import org.ucb.dentatrack.Configuration.Domain.Repository.RemoteConfigRepository

class ObserveRemoteConfigUseCase(
    private val repository:
    RemoteConfigRepository
) {

    operator fun invoke() =
        repository.observeConfig()
}