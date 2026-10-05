package org.ucb.dentatrack.ExchangeRate.Presentation.screen

import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.dentatrack.ExchangeRate.Presentation.composable.ExchangeRateContent
import org.ucb.dentatrack.ExchangeRate.Presentation.viewmodel.ExchangeRateEffect
import org.ucb.dentatrack.ExchangeRate.Presentation.viewmodel.ExchangeRateViewModel


@Composable
fun ExchangeRateScreen(
    viewModel: ExchangeRateViewModel =
        koinViewModel()
) {

    val state by
    viewModel.state.collectAsState()

    val snackbar =
        remember {
            SnackbarHostState()
        }

    LaunchedEffect(Unit) {

        viewModel.effect.collect {
                effect ->

            when (effect) {

                is ExchangeRateEffect
                .ShowMessage -> {

                    snackbar.showSnackbar(
                        effect.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbar)
        }
    ) {

        ExchangeRateContent(
            state = state,
            onEvent =
                viewModel::emitEvent
        )
    }
}