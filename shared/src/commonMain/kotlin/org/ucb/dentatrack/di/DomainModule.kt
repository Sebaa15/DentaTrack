package org.ucb.dentatrack.di

import org.ucb.dentatrack.Odontogram.Domain.UseCase.SyncOdontogramUseCase
import org.ucb.dentatrack.Logs.Domain.UseCase.SendLogsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.ucb.dentatrack.Configuration.Domain.UseCase.ObserveRemoteConfigUseCase
import org.ucb.dentatrack.Login.Domain.UseCase.LoginUseCase
import org.ucb.dentatrack.Login.Domain.UseCase.LogoutUseCase
import org.ucb.dentatrack.Notifications.Domain.UseCase.RefreshDeviceTokenUseCase
import org.ucb.dentatrack.Notifications.Domain.UseCase.RegisterDeviceTokenUseCase
import org.ucb.dentatrack.Notifications.Domain.UseCase.UnregisterDeviceTokenUseCase
import org.ucb.dentatrack.Odontogram.Domain.UseCase.GetOdontogramUseCase
import org.ucb.dentatrack.Odontogram.Domain.UseCase.RestoreOdontogramUseCase
import org.ucb.dentatrack.Register.Domain.UseCase.RegisterUseCase
import org.ucb.dentatrack.Login.Domain.UseCase.GetCurrentUserUseCase
import org.ucb.dentatrack.Odontogram.Domain.UseCase.SaveToothUseCase

val domainModule = module {

    singleOf(::LoginUseCase)
    singleOf(::RegisterUseCase)
    singleOf(::GetOdontogramUseCase)
    singleOf(::ObserveRemoteConfigUseCase)
    singleOf(::RegisterDeviceTokenUseCase)
    singleOf(::UnregisterDeviceTokenUseCase)
    singleOf(::LogoutUseCase)
    singleOf(::RefreshDeviceTokenUseCase)
    singleOf(::SendLogsUseCase)
    singleOf(::SyncOdontogramUseCase)
    singleOf(::RestoreOdontogramUseCase)
    singleOf(::GetCurrentUserUseCase)
    singleOf(::SaveToothUseCase)
}