package org.ucb.dentatrack.Crypto.presentation.viewModel

sealed interface CryptoEvent {
    data object LoadCrypto : CryptoEvent
    data object Retry: CryptoEvent
}