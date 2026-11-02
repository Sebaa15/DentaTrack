package org.ucb.dentatrack.Appointments.Presentation.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel

import org.ucb.dentatrack.Appointments.Presentation.Composable.AppointmentsContent
import org.ucb.dentatrack.Appointments.Presentation.Viewmodel.*

@Composable
fun AppointmentsScreen(
    userId: String,
    onBack: () -> Unit,
    viewModel: AppointmentsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(userId) {
        viewModel.emitEvent(
            AppointmentsEvent.Start(userId)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                AppointmentsEffect.NavigateBack -> onBack()
            }
        }
    }

    AppointmentsContent(
        state = state,

        onBack = {
            viewModel.emitEvent(
                AppointmentsEvent.BackClicked
            )
        },

        onReasonChanged = {
            viewModel.emitEvent(
                AppointmentsEvent.ReasonChanged(it)
            )
        },

        onDoctorChanged = {
            viewModel.emitEvent(
                AppointmentsEvent.DoctorChanged(it)
            )
        },

        onDateChanged = {
            viewModel.emitEvent(
                AppointmentsEvent.DateChanged(it)
            )
        },

        onTimeChanged = {
            viewModel.emitEvent(
                AppointmentsEvent.TimeChanged(it)
            )
        },

        onEdit = {
            viewModel.emitEvent(
                AppointmentsEvent.EditClicked(it)
            )
        },

        onNew = {
            viewModel.emitEvent(
                AppointmentsEvent.NewClicked
            )
        },

        onSave = {
            viewModel.emitEvent(
                AppointmentsEvent.SaveClicked
            )
        },

        onCancel = {
            viewModel.emitEvent(
                AppointmentsEvent.CancelRequested(it)
            )
        },

        onConfirmCancel = {
            viewModel.emitEvent(
                AppointmentsEvent.CancelConfirmed
            )
        },

        onDismissCancel = {
            viewModel.emitEvent(
                AppointmentsEvent.CancelDismissed
            )
        },

        onComplete = {
            viewModel.emitEvent(
                AppointmentsEvent.CompleteClicked(it)
            )
        }
    )
}