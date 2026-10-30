package org.ucb.dentatrack.TreatmentDetail.Presentation.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import edu.ucb.ds.components.buttons.MainButton

import org.ucb.dentatrack.Odontogram.Domain.Model.ToothStatus
import org.ucb.dentatrack.TreatmentDetail.Presentation.Viewmodel.TreatmentDetailState

@Composable
fun TreatmentDetailContent(
    state: TreatmentDetailState,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("Volver")
        }

        Text(
            text = "Detalle de pieza dental",
            style = MaterialTheme.typography.headlineMedium
        )

        if (state.isLoading) {

            CircularProgressIndicator()

        } else if (state.error != null) {

            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error
            )

        } else {

            state.tooth?.let { tooth ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(16.dp)
                    ) {

                        Text(
                            text = "Pieza ${tooth.number}",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        HorizontalDivider()

                        Text(
                            text = "Estado dental",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = getStatusText(tooth.status),
                                modifier = Modifier.padding(12.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        HorizontalDivider()

                        Text(
                            text = "Tratamiento u observación",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = tooth.treatment
                                ?.takeIf { it.isNotBlank() }
                                ?: "Sin tratamiento u observaciones registradas",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Text(
                    text = "La información corresponde al odontograma guardado del paciente.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        MainButton(
            text = "Volver al odontograma",
            onClick = onBack
        )
    }
}

private fun getStatusText(
    status: ToothStatus
): String {

    return when (status) {
        ToothStatus.HEALTHY -> "Saludable"
        ToothStatus.OBSERVATION -> "En observación"
        ToothStatus.IN_TREATMENT -> "En tratamiento"
        ToothStatus.COMPLETED -> "Tratamiento finalizado"
        ToothStatus.MISSING -> "Pieza ausente"
        ToothStatus.UNREGISTERED -> "Sin registro"
    }
}