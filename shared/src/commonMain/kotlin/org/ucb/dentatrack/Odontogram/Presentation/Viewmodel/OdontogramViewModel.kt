package org.ucb.dentatrack.Odontogram.Presentation.Viewmodel

import org.ucb.dentatrack.Odontogram.Domain.UseCase.SyncOdontogramUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.dentatrack.Configuration.Domain.UseCase.ObserveRemoteConfigUseCase
import org.ucb.dentatrack.Login.Domain.UseCase.LogoutUseCase
import org.ucb.dentatrack.Notifications.Domain.UseCase.UnregisterDeviceTokenUseCase
import org.ucb.dentatrack.Odontogram.Domain.UseCase.GetOdontogramUseCase
import org.ucb.dentatrack.Odontogram.Domain.UseCase.RestoreOdontogramUseCase
import org.ucb.dentatrack.sheduler.OdontogramWorkScheduler

class OdontogramViewModel(
    private val getOdontogramUseCase: GetOdontogramUseCase,
    private val observeRemoteConfigUseCase: ObserveRemoteConfigUseCase,
    private val unregisterDeviceTokenUseCase: UnregisterDeviceTokenUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val syncOdontogramUseCase: SyncOdontogramUseCase,
    private val restoreOdontogramUseCase: RestoreOdontogramUseCase,
    private val workScheduler: OdontogramWorkScheduler
) : ViewModel() {
    private var currentUserId: String? = null
    private val _state = MutableStateFlow(OdontogramState())
    val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<OdontogramEffect>()

    val effect = _effect.asSharedFlow()

    init {
        observeRemoteConfig()
    }

    fun emitEvent(
        event: OdontogramEvent
    ) {

        when (event) {
            OdontogramEvent.SyncClicked -> {
                syncOdontogram()
            }
            OdontogramEvent.RestoreClicked -> {
                restoreOdontogram()
            }
            is OdontogramEvent.Start -> {

                currentUserId =
                    event.userId

                loadOdontogram()
            }
            is OdontogramEvent.ToothSelected -> {

                val tooth =
                    state.value.teeth.find {
                        it.number ==
                                event.toothNumber
                    }

                _state.update {
                    it.copy(
                        selectedTooth = tooth
                    )
                }

                if (
                    tooth != null &&
                    state.value
                        .treatmentDetailEnabled
                ) {

                    emitEffect(
                        OdontogramEffect
                            .NavigateToTreatmentDetail(
                                tooth.number
                            )
                    )
                }
            }

            OdontogramEvent.LogoutClicked -> {
                logout()
            }
        }
    }

    private fun loadOdontogram() {
        val userId = currentUserId ?: return
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val teeth = getOdontogramUseCase(userId)

            _state.update {
                it.copy(
                    teeth = teeth,
                    isLoading = false
                )
            }
        }
    }

    private fun observeRemoteConfig() {

        viewModelScope.launch {

            observeRemoteConfigUseCase()
                .catch { error ->

                    println(
                        "REMOTE_CONFIG_ERROR: ${error.message}"
                    )
                }
                .collect { config ->

                    _state.update {
                        it.copy(
                            title =
                                config.odontogramTitle,

                            treatmentDetailEnabled =
                                config
                                    .enableTreatmentDetail
                        )
                    }
                }
        }
    }

    private fun logout() {

        viewModelScope.launch {

            currentUserId?.let {
                    userId ->

                runCatching {
                    unregisterDeviceTokenUseCase(
                        userId
                    )
                }
            }

            logoutUseCase()
            workScheduler.cancel()
            emitEffect(
                OdontogramEffect
                    .NavigateToLogin
            )
        }
    }

    private fun emitEffect(
        effect: OdontogramEffect
    ) {

        viewModelScope.launch {
            _effect.emit(
                effect
            )
        }
    }
    private fun syncOdontogram() {

        val userId = currentUserId ?: return

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isSyncing = true,
                    syncMessage = null
                )
            }

            try {
                val cantidad = syncOdontogramUseCase(userId)

                _state.update {
                    it.copy(
                        isSyncing = false,
                        syncMessage = "$cantidad dientes enviados a Firestore"
                    )
                }

            } catch (e: Exception) {

                _state.update {
                    it.copy(
                        isSyncing = false,
                        syncMessage = "Error al sincronizar con Firestore"
                    )
                }
            }
        }
    }
    private fun restoreOdontogram() {

        val userId = currentUserId ?: return

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isSyncing = true,
                    syncMessage = null
                )
            }

            try {
                val result = restoreOdontogramUseCase(userId)

                _state.update {
                    it.copy(
                        isSyncing = false,
                        syncMessage =
                            "${result.remoteCount} dientes descargados, " +
                                    "${result.insertedCount} nuevos guardados en Room"
                    )
                }

            } catch (e: Exception) {

                _state.update {
                    it.copy(
                        isSyncing = false,
                        syncMessage = "Error al recuperar Firestore"
                    )
                }
            }
        }
    }
}