package org.ucb.dentatrack.TreatmentPlan.Presentation.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import edu.ucb.ds.components.fields.MainTextField

import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStep
import org.ucb.dentatrack.TreatmentPlan.Domain.Model.TreatmentStepStatus
import org.ucb.dentatrack.TreatmentPlan.Presentation.Viewmodel.TreatmentPlanState

@Composable
fun TreatmentPlanContent(
    state: TreatmentPlanState,
    onBack: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit,
    onStatusChanged: (TreatmentStepStatus) -> Unit,
    onEditStep: (Int) -> Unit,
    onNewStep: () -> Unit,
    onSave: () -> Unit
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
            text = "Plan de tratamiento",
            style = MaterialTheme.typography.headlineMedium
        )

        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.userId == null) {
            Text(
                text = state.error ?: "Sesión no disponible",
                color = MaterialTheme.colorScheme.error
            )
        } else {

            // Tarjeta de progreso
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Progreso general",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "${state.completedCount} / ${state.steps.size} etapas",
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(
                                MaterialTheme.colorScheme
                                    .onPrimary.copy(alpha = 0.25f),
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(state.progress)
                                .height(8.dp)
                                .background(
                                    MaterialTheme.colorScheme.onPrimary,
                                    RoundedCornerShape(8.dp)
                                )
                        )
                    }

                    Text(
                        text = "${(state.progress * 100).toInt()}% completado",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // Etapas registradas
            Text(
                text = "Etapas del tratamiento",
                style = MaterialTheme.typography.titleLarge
            )

            if (state.steps.isEmpty()) {
                Text("Todavía no existen etapas registradas")
            }

            state.steps.forEach { step ->
                TreatmentStepCard(
                    step = step,
                    onEdit = { onEditStep(step.id) }
                )
            }

            HorizontalDivider()

            // Formulario
            Text(
                text = if (state.editingId == 0)
                    "Nueva etapa"
                else
                    "Editar etapa",
                style = MaterialTheme.typography.titleLarge
            )

            MainTextField(
                value = state.title,
                onValueChange = onTitleChanged,
                label = "Nombre de la etapa"
            )

            MainTextField(
                value = state.date,
                onValueChange = onDateChanged,
                label = "Fecha o referencia"
            )

            MainTextField(
                value = state.note,
                onValueChange = onNoteChanged,
                label = "Observaciones"
            )

            Text(
                text = "Estado de la etapa",
                style = MaterialTheme.typography.titleMedium
            )

            val statuses = listOf(
                TreatmentStepStatus.PENDING to "Pendiente",
                TreatmentStepStatus.IN_PROGRESS to "En curso",
                TreatmentStepStatus.COMPLETED to "Completada"
            )

            statuses.forEach { (status, label) ->

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = state.editingStatus == status,
                        enabled = !state.isSaving,
                        onClick = {
                            onStatusChanged(status)
                        }
                    )

                    Text(label)
                }
            }

            Button(
                onClick = onSave,
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (state.isSaving)
                        "Guardando..."
                    else
                        "Guardar etapa"
                )
            }

            if (state.editingId != 0) {
                OutlinedButton(
                    onClick = onNewStep,
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar edición")
                }
            }

            state.message?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun TreatmentStepCard(
    step: TreatmentStep,
    onEdit: () -> Unit
) {
    val statusText = when (step.status) {
        TreatmentStepStatus.PENDING -> "Pendiente"
        TreatmentStepStatus.IN_PROGRESS -> "En curso"
        TreatmentStepStatus.COMPLETED -> "Completada"
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (step.status) {
                        TreatmentStepStatus.COMPLETED -> "✓"
                        TreatmentStepStatus.IN_PROGRESS -> "●"
                        TreatmentStepStatus.PENDING -> "○"
                    },
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = step.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Estado: $statusText",
                style = MaterialTheme.typography.bodyMedium
            )

            if (step.date.isNotBlank()) {
                Text(
                    text = "Fecha: ${step.date}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (step.note.isNotBlank()) {
                Text(
                    text = step.note,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            OutlinedButton(onClick = onEdit) {
                Text("Editar etapa")
            }
        }
    }
}