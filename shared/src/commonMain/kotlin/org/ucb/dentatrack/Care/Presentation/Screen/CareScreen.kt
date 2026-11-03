package org.ucb.dentatrack.Care.Presentation.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import org.koin.compose.viewmodel.koinViewModel

import org.ucb.dentatrack.Care.Platform.todayLocalDate
import org.ucb.dentatrack.Care.Presentation.Composable.CareContent
import org.ucb.dentatrack.Care.Presentation.Viewmodel.*

@Composable
fun CareScreen(
    userId: String,
    onBack: () -> Unit,
    viewModel: CareViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(userId) {
        viewModel.emitEvent(
            CareEvent.Start(
                userId = userId,
                date = todayLocalDate()
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CareEffect.NavigateBack -> onBack()
            }
        }
    }

    CareContent(
        state = state,

        onBack = {
            viewModel.emitEvent(
                CareEvent.BackClicked
            )
        },

        onTitleChanged = {
            viewModel.emitEvent(
                CareEvent.TitleChanged(it)
            )
        },

        onNoteChanged = {
            viewModel.emitEvent(
                CareEvent.NoteChanged(it)
            )
        },

        onAdd = {
            viewModel.emitEvent(
                CareEvent.AddClicked
            )
        },

        onCheckedChanged = { careId, completed ->
            viewModel.emitEvent(
                CareEvent.CheckedChanged(
                    careId = careId,
                    completed = completed
                )
            )
        }
    )
}