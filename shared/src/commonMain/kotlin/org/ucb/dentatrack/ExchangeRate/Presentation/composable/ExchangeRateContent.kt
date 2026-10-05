package org.ucb.dentatrack.ExchangeRate.Presentation.composable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ucb.dentatrack.ExchangeRate.Presentation.viewmodel.ExchangeRateEvent
import org.ucb.dentatrack.ExchangeRate.Presentation.viewmodel.ExchangeRateState

@Composable
fun ExchangeRateContent(
    state: ExchangeRateState,
    onEvent: (ExchangeRateEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Cotización del dólar",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (state.showAdd) {

            OutlinedTextField(
                value = state.newRate,
                onValueChange = {
                    onEvent(
                        ExchangeRateEvent
                            .RateChanged(it)
                    )
                },
                label = {
                    Text("Nueva cotización")
                },
                modifier =
                    Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row {

                Button(
                    onClick = {
                        onEvent(
                            ExchangeRateEvent
                                .SaveClicked
                        )
                    }
                ) {
                    Text("Guardar")
                }

                Spacer(
                    Modifier.width(8.dp)
                )

                OutlinedButton(
                    onClick = {
                        onEvent(
                            ExchangeRateEvent
                                .CancelClicked
                        )
                    }
                ) {
                    Text("Cancelar")
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }

        if (state.isLoading) {

            Box(
                modifier =
                    Modifier.fillMaxWidth(),
                contentAlignment =
                    Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {

            LazyColumn(
                modifier =
                    Modifier.weight(1f),
                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                items(state.rates) { rate ->

                    Card(
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text =
                                rate.value.toString(),
                            modifier =
                                Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                onEvent(
                    ExchangeRateEvent.AddClicked
                )
            },
            modifier =
                Modifier.fillMaxWidth()
        ) {
            Text("+ Agregar")
        }
    }
}