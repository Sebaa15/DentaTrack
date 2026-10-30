package org.ucb.dentatrack.TreatmentDetail.Presentation.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import org.koin.compose.viewmodel.koinViewModel

import org.ucb.dentatrack.TreatmentDetail.Presentation.Composable.TreatmentDetailContent
import org.ucb.dentatrack.TreatmentDetail.Presentation.Viewmodel.TreatmentDetailEffect
import org.ucb.dentatrack.TreatmentDetail.Presentation.Viewmodel.TreatmentDetailEvent
import org.ucb.dentatrack.TreatmentDetail.Presentation.Viewmodel.TreatmentDetailViewModel

@Composable
fun TreatmentDetailScreen(
    userId: String,
    toothNumber: Int,
    onBack: () -> Unit,
    viewModel: TreatmentDetailViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(userId, toothNumber) {

        viewModel.emitEvent(
            TreatmentDetailEvent.Start(
                userId = userId,
                toothNumber = toothNumber
            )
        )
    }

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->

            when (effect) {

                TreatmentDetailEffect.NavigateBack -> {
                    onBack()
                }
            }
        }
    }

    TreatmentDetailContent(
        state = state,
        onBack = {
            viewModel.emitEvent(
                TreatmentDetailEvent.BackClicked
            )
        }
    )
}