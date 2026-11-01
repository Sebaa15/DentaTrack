package org.ucb.dentatrack.Home.Presentation.Screen


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel

import org.ucb.dentatrack.Home.Presentation.Composable.HomeContent
import org.ucb.dentatrack.Home.Presentation.Viewmodel.HomeEffect
import org.ucb.dentatrack.Home.Presentation.Viewmodel.HomeEvent
import org.ucb.dentatrack.Home.Presentation.Viewmodel.HomeViewModel
import org.ucb.dentatrack.navigation.NavRoute

@Composable
fun HomeScreen(
    navController: NavHostController,
    userId: String,
    onOpenSettings: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(userId, navController) {

        navController.currentBackStackEntryFlow.collect { entry ->

            if (entry.destination.hasRoute<NavRoute.Home>()) {

                viewModel.emitEvent(
                    HomeEvent.Start(userId)
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->

            when (effect) {
                is HomeEffect.NavigateToOdontogram -> {
                    navController.navigate(
                        NavRoute.Odontogram(effect.userId)
                    )
                }

                HomeEffect.NavigateToLogin -> {
                    navController.navigate(NavRoute.Login) {
                        popUpTo<NavRoute.Home> {
                            inclusive = true
                        }
                    }
                }
                is HomeEffect.NavigateToPlan -> {

                    navController.navigate(
                        NavRoute.TreatmentPlan(effect.userId)
                    )
                }
            }
        }
    }

    HomeContent(
        state = state,
        onOdontogramClick = {
            viewModel.emitEvent(
                HomeEvent.OdontogramClicked
            )
        },
        onSettings = onOpenSettings,
        onPlanClick = {
            viewModel.emitEvent(
                HomeEvent.PlanClicked
            )
        },
    )
}