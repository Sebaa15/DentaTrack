package org.ucb.dentatrack.Login.presentation.Screen

import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.dentatrack.Login.presentation.Composable.LoginContent
import org.ucb.dentatrack.Login.presentation.Viewmodel.LoginEffect
import org.ucb.dentatrack.Login.presentation.Viewmodel.LoginViewModel
import org.ucb.dentatrack.navigation.NavRoute

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToOdontogram ->
                    navController.navigate(NavRoute.Odontogram)

                is LoginEffect.ShowMessage -> {
                    // Luego conectaremos Snackbar o Toast
                }
            }
        }
    }
    LoginContent(
        state = state,
        onEvent = viewModel::emitEvent,
        onRegisterClick = {
            navController.navigate(
                NavRoute.Register)
        }
    )
}