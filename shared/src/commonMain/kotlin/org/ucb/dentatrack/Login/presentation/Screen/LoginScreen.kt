package org.ucb.dentatrack.Login.presentation.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.dentatrack.Login.presentation.Composable.LoginContent
import org.ucb.dentatrack.Login.presentation.Viewmodel.LoginEffect
import org.ucb.dentatrack.Login.presentation.Viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    viewModel: LoginViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->

            when (effect) {

                LoginEffect.NavigateToOdontogram -> {
                    onLoginSuccess()
                }

                is LoginEffect.ShowMessage -> {
                    // Luego conectaremos Snackbar
                }
            }
        }
    }

    LoginContent(
        state = state,
        onEvent = viewModel::emitEvent,
        onRegisterClick = onRegisterClick
    )
}