package org.ucb.dentatrack.ExchangeRate.Presentation.viewmodel

import org.ucb.dentatrack.ExchangeRate.Domain.model.ExchangeRate

data class ExchangeRateState(
    val rates:List<ExchangeRate> = emptyList(),
    val newRate: String="",
    val showAdd: Boolean=false,
    val isLoading: Boolean=false
)