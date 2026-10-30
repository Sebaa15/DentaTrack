package org.ucb.dentatrack.Odontogram.Presentation.Composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dentatrack.shared.generated.resources.Res
import dentatrack.shared.generated.resources.logout_button
import edu.ucb.ds.components.fields.MainTextField
import org.jetbrains.compose.resources.stringResource
import org.ucb.dentatrack.Odontogram.Domain.Model.*

import org.ucb.dentatrack.Odontogram.Presentation.Viewmodel.OdontogramState

@Composable
fun OdontogramContent(
    state: OdontogramState,
    onToothSelected: (Int) -> Unit,
    onViewTreatment: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onSync: () -> Unit,
    onRestore: () -> Unit,
    onSettings: () -> Unit,
    onStatusChanged: (ToothStatus) -> Unit,
    onTreatmentChanged: (String) -> Unit,
    onSaveTooth: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text("Volver")
            }

            Text(
                text = state.title,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Text(
            text = "Selecciona una pieza dental para consultar su estado",
            style = MaterialTheme.typography.bodyMedium
        )

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {

            // Odontograma
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Arcada superior",
                        style = MaterialTheme.typography.titleSmall
                    )

                    // Ambas arcadas comparten el desplazamiento
                    // horizontal para mantener la alineación.
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalArrangement =
                            Arrangement.spacedBy(16.dp)
                    ) {

                        ToothRow(
                            numbers = UPPER_TOOTH_NUMBERS,
                            state = state,
                            onToothSelected = onToothSelected
                        )

                        ToothRow(
                            numbers = LOWER_TOOTH_NUMBERS,
                            state = state,
                            onToothSelected = onToothSelected
                        )
                    }

                    Text(
                        text = "Arcada inferior",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = "Desliza horizontalmente para ver todas las piezas",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Leyenda
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Estados dentales",
                        style = MaterialTheme.typography.titleMedium
                    )

                    ToothLegend(
                        ToothStatus.HEALTHY,
                        "Saludable"
                    )
                    ToothLegend(
                        ToothStatus.OBSERVATION,
                        "En observación"
                    )
                    ToothLegend(
                        ToothStatus.IN_TREATMENT,
                        "En tratamiento"
                    )
                    ToothLegend(
                        ToothStatus.COMPLETED,
                        "Tratamiento finalizado"
                    )
                    ToothLegend(
                        ToothStatus.MISSING,
                        "Pieza ausente"
                    )
                    ToothLegend(
                        ToothStatus.UNREGISTERED,
                        "Sin registro"
                    )
                }
            }

            // Información de la pieza seleccionada
            state.selectedTooth?.let { tooth ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = "Pieza ${tooth.number}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Estado: ${statusText(tooth.status)}"
                        )

                        tooth.treatment?.let { treatment ->
                            Text(
                                text = "Tratamiento: $treatment"
                            )
                        }
                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = "Actualizar estado",
                            style = MaterialTheme.typography.titleSmall
                        )

                        val statuses = listOf(
                            ToothStatus.HEALTHY to "Saludable",
                            ToothStatus.OBSERVATION to "En observación",
                            ToothStatus.IN_TREATMENT to "En tratamiento",
                            ToothStatus.COMPLETED to "Finalizado",
                            ToothStatus.MISSING to "Pieza ausente"
                        )

                        statuses.forEach { (status, label) ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 48.dp)
                                    .clickable(
                                        enabled = !state.isSaving
                                    ) {
                                        onStatusChanged(status)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                RadioButton(
                                    selected = state.editingStatus == status,
                                    onClick = null,
                                    enabled = !state.isSaving
                                )

                                Text(text = label)
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        MainTextField(
                            value = state.editingTreatment,
                            onValueChange = onTreatmentChanged,
                            label = "Tratamiento u observación"
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = onSaveTooth,
                            enabled = state.editingStatus != null &&
                                    !state.isSaving &&
                                    !state.isSyncing,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                if (state.isSaving)
                                    "Guardando..."
                                else
                                    "Guardar cambios"
                            )
                        }

                        state.saveMessage?.let { message ->
                            Text(
                                text = message,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        if (
                            state.treatmentDetailEnabled &&
                            tooth.status != ToothStatus.UNREGISTERED
                        ) {
                            Button(
                                onClick = onViewTreatment
                            ) {
                                Text("Ver detalle")
                            }
                        }
                    }
                }
            }
        }

        // Acciones existentes de Firebase
        Button(
            onClick = onSync,
            enabled = !state.isSyncing,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (state.isSyncing)
                    "Sincronizando..."
                else
                    "Subir a Firestore (prueba)"
            )
        }

        OutlinedButton(
            onClick = onRestore,
            enabled = !state.isSyncing,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Recuperar desde Firestore")
        }

        state.syncMessage?.let {
            Text(text = it)
        }

        TextButton(onClick = onSettings) {
            Text("Accesibilidad")
        }

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(Res.string.logout_button)
            )
        }
    }
}

@Composable
private fun ToothRow(
    numbers: List<Int>,
    state: OdontogramState,
    onToothSelected: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        numbers.forEach { number ->

            val tooth = state.teeth.find {
                it.number == number
            }

            if (tooth != null) {
                ToothItem(
                    tooth = tooth,
                    selected =
                        state.selectedTooth?.number == number,
                    onClick = {
                        onToothSelected(number)
                    }
                )
            }
        }
    }
}

@Composable
private fun ToothItem(
    tooth: Tooth,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .width(48.dp)
            .heightIn(min = 64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (selected)
                    colors.primaryContainer
                else
                    colors.surface
            )
            .clickable(onClick = onClick)
            .semantics(mergeDescendants = true) {
                contentDescription =
                    "Pieza ${tooth.number}, " +
                            statusText(tooth.status)
            }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        ToothShape(
            status = tooth.status
        )

        Text(
            text = tooth.number.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = if (selected)
                colors.primary
            else
                colors.onSurface
        )
    }
}

@Composable
private fun ToothShape(
    status: ToothStatus
) {
    val colors = MaterialTheme.colorScheme
    val fillColor = toothColor(status)
    val strokeColor = colors.outline
    Canvas(
        modifier = Modifier.size(
            width = 26.dp,
            height = 29.dp
        )
    ) {
        val toothPath = Path().apply {

            moveTo(4f, 2f)

            cubicTo(4f, 2f, 1f, 3f, 1f, 7f)
            cubicTo(1f, 11f, 2f, 14f, 3f, 17f)
            cubicTo(4f, 20f, 5f, 22f, 8f, 22f)
            cubicTo(9.5f, 22f, 10f, 20f, 11f, 20f)
            cubicTo(12f, 20f, 12.5f, 22f, 14f, 22f)
            cubicTo(17f, 22f, 18f, 20f, 19f, 17f)
            cubicTo(20f, 14f, 21f, 11f, 21f, 7f)
            cubicTo(21f, 3f, 18f, 2f, 18f, 2f)
            cubicTo(16f, 1f, 14f, 2f, 11f, 2f)
            cubicTo(8f, 2f, 6f, 1f, 4f, 2f)

            close()
        }

        withTransform({
            scale(
                scaleX = size.width / 22f,
                scaleY = size.height / 24f,
                pivot = Offset.Zero
            )
        }) {

            drawPath(
                path = toothPath,
                color = fillColor
            )

            drawPath(
                path = toothPath,
                color = strokeColor,
                style = Stroke(width = 1.2f)
            )

            if (status == ToothStatus.IN_TREATMENT) {
                drawCircle(
                    color = Color.White.copy(alpha = 0.5f),
                    radius = 3.5f,
                    center = Offset(11f, 12f)
                )
            }
        }
    }
}

@Composable
private fun ToothLegend(
    status: ToothStatus,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(toothColor(status))
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                    CircleShape
                )
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun toothColor(
    status: ToothStatus
): Color {
    val colors = MaterialTheme.colorScheme

    return when (status) {
        ToothStatus.HEALTHY -> colors.surface
        ToothStatus.OBSERVATION -> colors.tertiaryContainer
        ToothStatus.IN_TREATMENT -> colors.error
        ToothStatus.COMPLETED -> colors.primary
        ToothStatus.MISSING -> colors.outlineVariant
        ToothStatus.UNREGISTERED ->
            colors.surfaceVariant.copy(alpha = 0.45f)
    }
}

private fun statusText(
    status: ToothStatus
): String {
    return when (status) {
        ToothStatus.HEALTHY -> "Saludable"
        ToothStatus.OBSERVATION -> "En observación"
        ToothStatus.IN_TREATMENT -> "En tratamiento"
        ToothStatus.COMPLETED -> "Finalizado"
        ToothStatus.MISSING -> "Pieza ausente"
        ToothStatus.UNREGISTERED -> "Sin registro"
    }
}