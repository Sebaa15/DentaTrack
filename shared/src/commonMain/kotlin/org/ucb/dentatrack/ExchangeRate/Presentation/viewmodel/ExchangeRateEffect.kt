package org.ucb.dentatrack.ExchangeRate.Presentation.viewmodel

sealed interface ExchangeRateEffect {
    data class ShowMessage(
        val message: String
    ) : ExchangeRateEffect
}