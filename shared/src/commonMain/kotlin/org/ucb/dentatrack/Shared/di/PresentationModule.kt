package org.ucb.dentatrack.Shared.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.dentatrack.Login.presentation.State.LoginViewModel
import org.ucb.dentatrack.Register.presentation.State.RegisterViewModel

val presentationModule = module {

    viewModelOf(::LoginViewModel)

    viewModelOf(::RegisterViewModel)
}