package org.ucb.dentatrack.Appointments.Presentation.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import edu.ucb.ds.components.fields.MainTextField

import org.ucb.dentatrack.Appointments.Domain.Model.Appointment
import org.ucb.dentatrack.Appointments.Domain.Model.AppointmentStatus
import org.ucb.dentatrack.Appointments.Presentation.Viewmodel.AppointmentsState

@Composable
fun AppointmentsContent(
    state: AppointmentsState,
    onBack: () -> Unit,
    onReasonChanged: (String) -> Unit,
    onDoctorChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onTimeChanged: (String) -> Unit,
    onEdit: (Int) -> Unit,
    onNew: () -> Unit,
    onSave: () -> Unit,
    onCancel: (Int) -> Unit,
    onConfirmCancel: () -> Unit,
    onDismissCancel: () -> Unit,
    onComplete: (Int) -> Unit
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
            text = "Mis citas",
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

            val scheduled = state.appointments.filter {
                it.status == AppointmentStatus.SCHEDULED
            }

            val history = state.appointments.filter {
                it.status != AppointmentStatus.SCHEDULED
            }.reversed()

            Text(
                text = "Próxima cita agendada",
                style = MaterialTheme.typography.titleLarge
            )

            if (scheduled.isEmpty()) {

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "No tienes citas agendadas",
                        modifier = Modifier.padding(20.dp)
                    )
                }

            } else {

                AppointmentCard(
                    appointment = scheduled.first(),
                    highlighted = true,
                    enabled = !state.isSaving,
                    onEdit = {
                        onEdit(scheduled.first().id)
                    },
                    onCancel = {
                        onCancel(scheduled.first().id)
                    },
                    onComplete = {
                        onComplete(scheduled.first().id)
                    }
                )
            }

            if (scheduled.size > 1) {

                Text(
                    text = "Otras citas agendadas",
                    style = MaterialTheme.typography.titleMedium
                )

                scheduled.drop(1).forEach { appointment ->

                    AppointmentCard(
                        appointment = appointment,
                        highlighted = false,
                        enabled = !state.isSaving,
                        onEdit = {
                            onEdit(appointment.id)
                        },
                        onCancel = {
                            onCancel(appointment.id)
                        },
                        onComplete = {
                            onComplete(appointment.id)
                        }
                    )
                }
            }

            HorizontalDivider()

            Text(
                text = if (state.editingId == 0)
                    "Agendar nueva cita"
                else
                    "Reprogramar cita",
                style = MaterialTheme.typography.titleLarge
            )

            MainTextField(
                value = state.reason,
                onValueChange = onReasonChanged,
                label = "Motivo de la cita"
            )

            MainTextField(
                value = state.doctor,
                onValueChange = onDoctorChanged,
                label = "Odontólogo"
            )

            MainTextField(
                value = state.date,
                onValueChange = onDateChanged,
                label = "Fecha (AAAA-MM-DD)"
            )

            MainTextField(
                value = state.time,
                onValueChange = onTimeChanged,
                label = "Hora (HH:mm)"
            )

            Text(
                text = "Ejemplo de formato: 2026-11-24 y 16:30",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = onSave,
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (state.isSaving)
                        "Guardando..."
                    else if (state.editingId == 0)
                        "Agendar cita"
                    else
                        "Guardar reprogramación"
                )
            }

            if (state.editingId != 0) {
                OutlinedButton(
                    onClick = onNew,
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar edición")
                }
            }

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.message?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            HorizontalDivider()

            Text(
                text = "Historial",
                style = MaterialTheme.typography.titleLarge
            )

            if (history.isEmpty()) {
                Text("Todavía no tienes citas en el historial")
            }

            history.forEach { appointment ->
                AppointmentHistoryCard(appointment)
            }
        }
    }

    if (state.pendingCancelId != null) {

        AlertDialog(
            onDismissRequest = onDismissCancel,
            title = {
                Text("Cancelar cita")
            },
            text = {
                Text(
                    "¿Estás seguro de que deseas cancelar esta cita?"
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirmCancel) {
                    Text("Sí, cancelar")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissCancel) {
                    Text("Volver")
                }
            }
        )
    }
}

@Composable
private fun AppointmentCard(
    appointment: Appointment,
    highlighted: Boolean,
    enabled: Boolean,
    onEdit: () -> Unit,
    onCancel: () -> Unit,
    onComplete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (highlighted)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = appointment.reason,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "${appointment.date} · ${appointment.time}",
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = appointment.doctor,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedButton(
                    onClick = onCancel,
                    enabled = enabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = onEdit,
                    enabled = enabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reprogramar")
                }
            }

            TextButton(
                onClick = onComplete,
                enabled = enabled
            ) {
                Text("Marcar como realizada")
            }
        }
    }
}

@Composable
private fun AppointmentHistoryCard(
    appointment: Appointment
) {
    val statusText = when (appointment.status) {
        AppointmentStatus.COMPLETED -> "Realizada"
        AppointmentStatus.CANCELLED -> "Cancelada"
        AppointmentStatus.SCHEDULED -> "Agendada"
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = appointment.reason,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "${appointment.date} · ${appointment.time}"
            )

            Text(
                text = appointment.doctor,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = statusText,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}