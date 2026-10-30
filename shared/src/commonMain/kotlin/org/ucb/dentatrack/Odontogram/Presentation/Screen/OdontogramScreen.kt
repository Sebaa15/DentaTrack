package org.ucb.dentatrack.Odontogram.Presentation.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.dentatrack.Odontogram.Presentation.Composable.OdontogramContent
import org.ucb.dentatrack.Odontogram.Presentation.Viewmodel.OdontogramEffect
import org.ucb.dentatrack.Odontogram.Presentation.Viewmodel.OdontogramEvent
import org.ucb.dentatrack.Odontogram.Presentation.Viewmodel.OdontogramViewModel
import org.ucb.dentatrack.navigation.NavRoute

@Composable
fun OdontogramScreen(
    navController: NavHostController,
    userId: String,
    viewModel: OdontogramViewModel =
        koinViewModel(),
    onOpenSettings: () -> Unit
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(userId) {

        viewModel.emitEvent(
            OdontogramEvent.Start(
                userId
            )
        )
    }

    LaunchedEffect(Unit){
        viewModel.effect.collect{ effect->
            when(effect){
                is OdontogramEffect.NavigateToTreatmentDetail -> {

                    navController.navigate(
                        NavRoute.TreatmentDetail(
                            userId = userId,
                            toothNumber = effect.toothNumber
                        )
                    )
                }
                OdontogramEffect.NavigateToLogin -> {

                    navController.navigate(
                        NavRoute.Login
                    ) {
                        popUpTo<NavRoute.Home> {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
    OdontogramContent(
        onStatusChanged = { status ->
            viewModel.emitEvent(
                OdontogramEvent.StatusChanged(status)
            )
        },

        onTreatmentChanged = { treatment ->
            viewModel.emitEvent(
                OdontogramEvent.TreatmentChanged(treatment)
            )
        },

        onSaveTooth = {
            viewModel.emitEvent(
                OdontogramEvent.SaveClicked
            )
        },
        onViewTreatment = {
            viewModel.emitEvent(
                OdontogramEvent.ViewTreatmentClicked
            )
        },

        onBack = {
            navController.popBackStack()
        },
        state=state, onToothSelected = {toothNumber->
            viewModel.emitEvent(
                OdontogramEvent.ToothSelected(toothNumber)
            )
        },
        onLogout = {

            viewModel.emitEvent(
                OdontogramEvent
                    .LogoutClicked
            )
        },
        onSync = {
            viewModel.emitEvent(
                OdontogramEvent.SyncClicked
            )
        },
        onRestore = {
            viewModel.emitEvent(
                OdontogramEvent.RestoreClicked
            )
        },
        onSettings = onOpenSettings
    )
}