package org.ucb.dentatrack.ExchangeRate.Presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.dentatrack.ExchangeRate.Domain.usecase.GetExchangeRatesUseCase
import org.ucb.dentatrack.ExchangeRate.Domain.usecase.SaveExchangeRateUseCase


class ExchangeRateViewModel(
    private val getExchangeRatesUseCase:
    GetExchangeRatesUseCase,
    private val saveExchangeRateUseCase:
    SaveExchangeRateUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            ExchangeRateState()
        )

    val state =
        _state.asStateFlow()

    private val _effect =
        MutableSharedFlow<ExchangeRateEffect>()

    val effect =
        _effect.asSharedFlow()

    init {
        emitEvent(
            ExchangeRateEvent.LoadRates
        )
    }

    fun emitEvent(
        event: ExchangeRateEvent
    ) {
        when (event) {

            ExchangeRateEvent.LoadRates ->
                loadRates()

            ExchangeRateEvent.AddClicked ->
                _state.update {
                    it.copy(showAdd = true)
                }

            ExchangeRateEvent.CancelClicked ->
                _state.update {
                    it.copy(
                        showAdd = false,
                        newRate = ""
                    )
                }

            is ExchangeRateEvent.RateChanged ->
                _state.update {
                    it.copy(
                        newRate = event.value
                    )
                }

            ExchangeRateEvent.SaveClicked ->
                saveRate()
        }
    }

    private fun loadRates() {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val rates =
                getExchangeRatesUseCase()

            _state.update {
                it.copy(
                    rates = rates,
                    isLoading = false
                )
            }
        }
    }

    private fun saveRate() {

        val value =
            state.value.newRate
                .toDoubleOrNull()

        if (value == null || value <= 0) {

            viewModelScope.launch {
                _effect.emit(
                    ExchangeRateEffect.ShowMessage(
                        "Ingrese una cotización válida"
                    )
                )
            }

            return
        }

        viewModelScope.launch {

            saveExchangeRateUseCase(
                value
            )

            _state.update {
                it.copy(
                    newRate = "",
                    showAdd = false
                )
            }

            loadRates()
        }
    }
}