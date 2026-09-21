package org.ucb.dentatrack.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.ucb.dentatrack.Login.Domain.UseCase.LoginUseCase
import org.ucb.dentatrack.Odontogram.Domain.UseCase.GetOdontogramUseCase
import org.ucb.dentatrack.Register.Domain.UseCase.RegisterUseCase

val domainModule = module {

    singleOf(::LoginUseCase)

    singleOf(::RegisterUseCase)
    singleOf(::GetOdontogramUseCase)

}