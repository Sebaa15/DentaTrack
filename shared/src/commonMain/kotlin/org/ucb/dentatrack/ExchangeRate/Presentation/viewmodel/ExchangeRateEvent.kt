package org.ucb.dentatrack.ExchangeRate.Presentation.viewmodel

sealed interface ExchangeRateEvent {

    data object LoadRates :
        ExchangeRateEvent

    data object AddClicked :
        ExchangeRateEvent

    data object CancelClicked :
        ExchangeRateEvent

    data class RateChanged(
        val value: String
    ) : ExchangeRateEvent

    data object SaveClicked :
        ExchangeRateEvent
}