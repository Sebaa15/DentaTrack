package org.ucb.dentatrack.Crypto.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ucb.dentatrack.Crypto.presentation.viewModel.CryptoEvent
import org.ucb.dentatrack.Crypto.presentation.viewModel.CryptoState

@Composable
fun CryptoContent(
    state: CryptoState,
    onEvent: (CryptoEvent) -> Unit
) {
    when {
        state.isLoading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        state.error != null -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(state.error)

            Button(
                onClick = { onEvent(CryptoEvent.Retry) }
            ) {
                Text("Reintentar")
            }
        }

        else -> LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.cryptos) { crypto ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("LOGO")
                        }

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Column {

                            Text(
                                text = "#${crypto.marketCapRank} ${crypto.name}",
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = crypto.symbol.uppercase()
                            )

                            Text(
                                text = "Precio: $${crypto.currentPrice}"
                            )

                            Text(
                                text = "24h: ${crypto.priceChange24h}%"
                            )

                            Text(
                                text = "Market Cap: $${crypto.marketCap}"
                            )
                        }
                    }
                }
            }
        }
    }
}