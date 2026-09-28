package org.ucb.dentatrack.Crypto.presentation.viewModel

import org.ucb.dentatrack.Crypto.domain.model.CryptoModel

data class CryptoState(
    val cryptos: List<CryptoModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)