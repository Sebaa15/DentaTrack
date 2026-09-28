package org.ucb.dentatrack.Crypto.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.dentatrack.Crypto.presentation.composable.CryptoContent
import org.ucb.dentatrack.Crypto.presentation.viewModel.*

@Composable
fun CryptoScreen(
    viewModel: CryptoViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CryptoEffect.ShowMessage ->
                    snackbar.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->

        Box(Modifier.padding(padding)) {
            CryptoContent(
                state = state,
                onEvent = viewModel::emitEvent
            )
        }
    }
}