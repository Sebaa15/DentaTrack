package org.ucb.dentatrack.Movies.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.dentatrack.Movies.presentation.composable.CatalogContent
import org.ucb.dentatrack.Movies.presentation.viewmodel.CatalogEffect
import org.ucb.dentatrack.Movies.presentation.viewmodel.CatalogViewModel

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel =
        koinViewModel()
) {

    val state by
    viewModel.state.collectAsState()

    val snackbarHostState =
        remember {
            SnackbarHostState()
        }

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->

            when (effect) {

                is CatalogEffect.ShowMessage -> {

                    snackbarHostState.showSnackbar(
                        effect.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState
            )
        }
    ) { paddingValues ->

        Box(
            modifier =
                Modifier.padding(paddingValues)
        ) {
            CatalogContent(
                state = state,
                onEvent =
                    viewModel::emitEvent
            )
        }
    }
}