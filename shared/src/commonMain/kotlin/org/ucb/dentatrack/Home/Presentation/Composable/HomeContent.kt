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
    onSettings: () -> Unit
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
                InformationCard(
                    title = "Próxima cita",
                    description = "No tienes citas registradas"
                )

                // Progreso
                InformationCard(
                    title = "Progreso del tratamiento",
                    description = "Aún no tienes un plan registrado"
                )

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

                    PendingActionCard(
                        title = "Mi tratamiento",
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    PendingActionCard(
                        title = "Mis citas",
                        modifier = Modifier.weight(1f)
                    )

                    PendingActionCard(
                        title = "Cuidados",
                        modifier = Modifier.weight(1f)
                    )
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