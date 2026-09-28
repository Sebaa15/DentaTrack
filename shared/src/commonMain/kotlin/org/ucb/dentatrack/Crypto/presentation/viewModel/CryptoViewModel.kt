package org.ucb.dentatrack.Crypto.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.dentatrack.Crypto.domain.usecase.GetCryptoUseCase

class CryptoViewModel(
    private val getCryptoUseCase: GetCryptoUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CryptoState())
    val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<CryptoEffect>()
    val effect = _effect.asSharedFlow()

    init {
        emitEvent(CryptoEvent.LoadCrypto)
    }

    fun emitEvent(event: CryptoEvent) {
        when (event) {
            CryptoEvent.LoadCrypto,
            CryptoEvent.Retry -> loadCrypto()
        }
    }

    private fun loadCrypto() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }
            getCryptoUseCase.invoke().fold(
                onSuccess = { cryptos ->
                    _state.update {
                        it.copy(
                            cryptos = cryptos,
                            isLoading = false
                        )
                    }
                },
                onFailure = {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "No se pudieron cargar las criptomonedas"
                        )
                    }

                    _effect.emit(
                        CryptoEffect.ShowMessage(
                            "Error al cargar la informacion de las criptomonedas"
                        )
                    )
                }
            )
        }
    }
}