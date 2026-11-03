package org.ucb.dentatrack.Home.Presentation.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucb.ds.components.cards.MainCard
import org.ucb.dentatrack.Home.Presentation.Viewmodel.HomeState

@Composable
fun HomeContent(
    state: HomeState,
    onOdontogramClick: () -> Unit,
    onSettings: () -> Unit,
    onPlanClick: () -> Unit,
    onAppointmentsClick: () -> Unit,
    onCareClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
    ) {

        // Encabezado
        Surface(
            color = colors.inverseSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Bienvenido,",
                        color = colors.inverseOnSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = state.userName.ifBlank {
                            "DentaTrack"
                        },
                        color = colors.inverseOnSurface,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                TextButton(
                    onClick = onSettings
                ) {
                    Text(
                        text = "Ajustes",
                        color = colors.inverseOnSurface
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                Text(
                    text = state.error,
                    color = colors.error
                )
            } else {

                // Próxima cita
                if (state.appointmentError != null) {

                    InformationCard(
                        title = "Próxima cita",
                        description = state.appointmentError
                    )

                } else if (state.nextAppointment == null) {

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(12.dp)
                        ) {

                            Text(
                                text = "Próxima cita",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = "No tienes citas agendadas",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            TextButton(
                                onClick = onAppointmentsClick
                            ) {
                                Text("Agendar una cita")
                            }
                        }
                    }

                } else {

                    val appointment = state.nextAppointment

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(12.dp)
                        ) {

                            Text(
                                text = "Próxima cita agendada",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            Text(
                                text = "${appointment.date} · ${appointment.time}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            Text(
                                text = appointment.reason,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = appointment.doctor,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Button(
                                onClick = onAppointmentsClick,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Ver o reprogramar cita")
                            }
                        }
                    }
                }

                if (state.planError != null) {

                    InformationCard(
                        title = "Progreso del tratamiento",
                        description = state.planError
                    )

                } else if (state.totalSteps == 0) {

                    InformationCard(
                        title = "Progreso del tratamiento",
                        description = "Todavía no tienes etapas registradas"
                    )

                } else {

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween,
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Progreso del tratamiento",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = "${state.progressPercentage}%",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }

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
                                        .fillMaxWidth(state.planProgress)
                                        .fillMaxHeight()
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            MaterialTheme.shapes.small
                                        )
                                )
                            }

                            Text(
                                text = "${state.completedSteps} de " +
                                        "${state.totalSteps} etapas completadas",
                                style = MaterialTheme.typography.bodySmall
                            )

                            TextButton(
                                onClick = onPlanClick
                            ) {
                                Text("Ver plan completo")
                            }
                        }
                    }
                }

                Text(
                    text = "Accesos rápidos",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    MainCard(
                        onClick = onOdontogramClick,
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 110.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Odontograma",
                                style = MaterialTheme.typography.titleSmall
                            )

                            Text(
                                text = "Ver mis piezas dentales",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    MainCard(
                        onClick = onPlanClick,
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 110.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Mi tratamiento",
                                style = MaterialTheme.typography.titleSmall
                            )

                            Text(
                                text = "Ver mi plan y progreso",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    MainCard(
                        onClick = onAppointmentsClick,
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 110.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Mis citas",
                                style = MaterialTheme.typography.titleSmall
                            )

                            Text(
                                text = "Ver y agendar citas",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    MainCard(
                        onClick = onCareClick,
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 110.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Text(
                                text = "Mis cuidados",
                                style = MaterialTheme.typography.titleSmall
                            )

                            Text(
                                text = "Ver indicaciones diarias",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                InformationCard(
                    title = "Recordatorio",
                    description =
                        "Recuerda mantener tus controles dentales al día."
                )
            }
        }
    }
}

@Composable
private fun InformationCard(
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun PendingActionCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.heightIn(min = 110.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = "Próximamente",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}