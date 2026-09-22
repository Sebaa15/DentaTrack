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
fun OdontogramScreen(navController: NavHostController,
                     viewModel: OdontogramViewModel= koinViewModel()
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit){
        viewModel.emitEvent(OdontogramEvent.LoadOdontogram)
    }

    LaunchedEffect(Unit){
        viewModel.effect.collect{ effect->
            when(effect){
                is OdontogramEffect.NavigateToTreatmentDetail->{
                    navController.navigate(NavRoute.TreatmentDetail(toothNumber=effect.toothNumber))
                }
            }
        }
    }
    OdontogramContent(
        state=state, onToothSelected = {toothNumber->
            viewModel.emitEvent(
                OdontogramEvent.ToothSelected(toothNumber=toothNumber)
            )
        }
    )
}