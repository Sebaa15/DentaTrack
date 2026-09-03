package org.ucb.dentatrack.Login.presentation.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.ucb.dentatrack.Login.Data.Repository.FakeAuthRepository
import org.ucb.dentatrack.Login.Domain.UseCase.LoginUseCase
import org.ucb.dentatrack.Login.presentation.Composable.LoginContent
import org.ucb.dentatrack.Login.presentation.State.LoginEffect
import org.ucb.dentatrack.Login.presentation.State.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {

    val viewModel = remember {

        val repository = FakeAuthRepository()

        val loginUseCase = LoginUseCase(
            repository = repository
        )

        LoginViewModel(
            loginUseCase = loginUseCase
        )
    }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->

            when (effect) {

                LoginEffect.NavigateToOdontogram -> {
                    onLoginSuccess()
                }

                is LoginEffect.ShowMessage -> {
                    // Más adelante conectaremos Snackbar
                }
            }
        }
    }

    LoginContent(
        state = state,
        onIntent = viewModel::onIntent,
        onRegisterClick = onRegisterClick
    )
}