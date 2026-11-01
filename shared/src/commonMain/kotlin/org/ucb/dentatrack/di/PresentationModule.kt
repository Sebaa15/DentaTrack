package org.ucb.dentatrack.di

import org.ucb.dentatrack.Home.Presentation.Viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.dentatrack.Login.presentation.Viewmodel.LoginViewModel
import org.ucb.dentatrack.Odontogram.Presentation.Viewmodel.OdontogramViewModel
import org.ucb.dentatrack.Register.Presentation.VIewmodel.RegisterViewModel
import org.ucb.dentatrack.TreatmentDetail.Presentation.Viewmodel.TreatmentDetailViewModel
import org.ucb.dentatrack.TreatmentPlan.Presentation.Viewmodel.TreatmentPlanViewModel

val presentationModule = module {

    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::OdontogramViewModel)
    viewModelOf(::TreatmentDetailViewModel)
    viewModelOf(::TreatmentPlanViewModel)
}