package org.ucb.dentatrack.Care.Presentation.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import edu.ucb.ds.components.fields.MainTextField
import org.ucb.dentatrack.Care.Presentation.Viewmodel.CareState

@Composable
fun CareContent(
    state: CareState,
    onBack: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit,
    onAdd: () -> Unit,
    onCheckedChanged: (Int, Boolean) -> Unit
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
            text = "Mis cuidados",
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

            // Tratamiento y cita reales
            Card(
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text = "Tratamiento actual",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = state.currentTreatment
                            ?: "No hay tratamientos en curso"
                    )

                    HorizontalDivider()

                    Text(
                        text = "Próxima cita",
                        style = MaterialTheme.typography.titleSmall
                    )

                    val appointment = state.nextAppointment

                    if (appointment != null) {
                        Text(
                            text = appointment.reason
                        )
                        Text(
                            text = "${appointment.date} · ${appointment.time}"
                        )
                        Text(
                            text = appointment.doctor
                        )
                    } else {
                        Text("No hay próximas citas agendadas")
                    }
                }
            }

            // Progreso del día
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Indicaciones de hoy",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Fecha: ${state.date}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = "${state.completedCount} de " +
                                "${state.items.size} completadas"
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.shapes.small
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(state.progress)
                                .fillMaxHeight()
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.shapes.small
                                )
                        )
                    }
                }
            }

            // Checklist
            if (state.items.isEmpty()) {

                Text(
                    text = "Todavía no tienes indicaciones registradas"
                )

            } else {

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        state.items.forEach { item ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 48.dp),
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Checkbox(
                                    checked = item.completedToday,
                                    enabled = !state.isSaving,
                                    onCheckedChange = { checked ->
                                        onCheckedChanged(
                                            item.id,
                                            checked
                                        )
                                    }
                                )

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = item.title,
                                        style =
                                            MaterialTheme.typography.bodyMedium
                                    )

                                    if (item.note.isNotBlank()) {
                                        Text(
                                            text = item.note,
                                            style =
                                                MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme
                                                .onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            HorizontalDivider()
                        }
                    }
                }
            }

            HorizontalDivider()

            // Formulario
            Text(
                text = "Agregar indicación",
                style = MaterialTheme.typography.titleMedium
            )

            MainTextField(
                value = state.newTitle,
                onValueChange = onTitleChanged,
                label = "Indicación"
            )

            MainTextField(
                value = state.newNote,
                onValueChange = onNoteChanged,
                label = "Nota (opcional)"
            )

            Button(
                onClick = onAdd,
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (state.isSaving)
                        "Guardando..."
                    else
                        "Guardar indicación"
                )
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

            // Recordatorios: fase posterior
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Recordatorios",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Los recordatorios automáticos " +
                                "se configurarán en una próxima etapa."
                    )
                }
            }
        }
    }
}