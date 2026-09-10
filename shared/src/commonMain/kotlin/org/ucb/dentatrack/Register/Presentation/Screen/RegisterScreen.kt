package org.ucb.dentatrack.Register.Presentation.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.dentatrack.Register.Data.Repository.FakeRegisterRepository
import org.ucb.dentatrack.Register.Domain.UseCase.RegisterUseCase
import org.ucb.dentatrack.Register.Presentation.Composable.RegisterContent
import org.ucb.dentatrack.Register.presentation.State.RegisterViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit,
    viewModel:RegisterViewModel= koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isRegistered) {

        if (state.isRegistered) {
            onRegisterSuccess()
        }
    }
    RegisterContent(
        state = state,
        onFullNameChanged = viewModel::onFullNameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
        onRegisterClick = viewModel::register,
        onBackToLogin = onBackToLogin
    )
}