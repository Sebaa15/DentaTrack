package org.ucb.dentatrack.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.dentatrack.Login.presentation.Viewmodel.LoginViewModel
import org.ucb.dentatrack.Odontogram.Presentation.Viewmodel.OdontogramViewModel
import org.ucb.dentatrack.Register.Presentation.VIewmodel.RegisterViewModel

val presentationModule = module {

    viewModelOf(::LoginViewModel)

    viewModelOf(::RegisterViewModel)
    viewModelOf(::OdontogramViewModel)
}