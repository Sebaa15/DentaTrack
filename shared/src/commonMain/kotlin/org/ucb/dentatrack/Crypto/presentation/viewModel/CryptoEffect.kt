package org.ucb.dentatrack.Crypto.presentation.viewModel

sealed interface CryptoEffect {
    data class ShowMessage(val message: String) : CryptoEffect
}