package org.ucb.dentatrack.TreatmentPlan.Presentation.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel

import org.ucb.dentatrack.TreatmentPlan.Presentation.Composable.TreatmentPlanContent
import org.ucb.dentatrack.TreatmentPlan.Presentation.Viewmodel.*

@Composable
fun TreatmentPlanScreen(
    userId: String,
    onBack: () -> Unit,
    viewModel: TreatmentPlanViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(userId) {
        viewModel.emitEvent(
            TreatmentPlanEvent.Start(userId)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                TreatmentPlanEffect.NavigateBack -> onBack()
            }
        }
    }

    TreatmentPlanContent(
        state = state,
        onBack = {
            viewModel.emitEvent(
                TreatmentPlanEvent.BackClicked
            )
        },
        onTitleChanged = {
            viewModel.emitEvent(
                TreatmentPlanEvent.TitleChanged(it)
            )
        },
        onDateChanged = {
            viewModel.emitEvent(
                TreatmentPlanEvent.DateChanged(it)
            )
        },
        onNoteChanged = {
            viewModel.emitEvent(
                TreatmentPlanEvent.NoteChanged(it)
            )
        },
        onStatusChanged = {
            viewModel.emitEvent(
                TreatmentPlanEvent.StatusChanged(it)
            )
        },
        onEditStep = {
            viewModel.emitEvent(
                TreatmentPlanEvent.EditClicked(it)
            )
        },
        onNewStep = {
            viewModel.emitEvent(
                TreatmentPlanEvent.NewClicked
            )
        },
        onSave = {
            viewModel.emitEvent(
                TreatmentPlanEvent.SaveClicked
            )
        }
    )
}