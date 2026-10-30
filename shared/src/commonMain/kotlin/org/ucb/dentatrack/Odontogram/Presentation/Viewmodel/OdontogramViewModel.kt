package org.ucb.dentatrack.Odontogram.Presentation.Viewmodel

import org.ucb.dentatrack.Odontogram.Domain.Model.ToothStatus
import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Model.UPPER_TOOTH_NUMBERS
import org.ucb.dentatrack.Odontogram.Domain.Model.LOWER_TOOTH_NUMBERS
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
import org.ucb.dentatrack.Odontogram.Domain.UseCase.SaveToothUseCase
import org.ucb.dentatrack.sheduler.OdontogramWorkScheduler

class OdontogramViewModel(
    private val getOdontogramUseCase: GetOdontogramUseCase,
    private val observeRemoteConfigUseCase: ObserveRemoteConfigUseCase,
    private val unregisterDeviceTokenUseCase: UnregisterDeviceTokenUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val syncOdontogramUseCase: SyncOdontogramUseCase,
    private val restoreOdontogramUseCase: RestoreOdontogramUseCase,
    private val workScheduler: OdontogramWorkScheduler,
    private val saveToothUseCase: SaveToothUseCase
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
            is OdontogramEvent.StatusChanged -> {

                if (!state.value.isSaving) {
                    _state.update {
                        it.copy(
                            editingStatus = event.status,
                            saveMessage = null
                        )
                    }
                }
            }

            is OdontogramEvent.TreatmentChanged -> {

                if (!state.value.isSaving) {
                    _state.update {
                        it.copy(
                            editingTreatment = event.treatment,
                            saveMessage = null
                        )
                    }
                }
            }

            OdontogramEvent.SaveClicked -> {
                saveSelectedTooth()
            }
            OdontogramEvent.ViewTreatmentClicked -> {

                val tooth = state.value.selectedTooth

                if (
                    tooth != null &&
                    tooth.status != ToothStatus.UNREGISTERED &&
                    state.value.treatmentDetailEnabled
                ) {
                    emitEffect(
                        OdontogramEffect.NavigateToTreatmentDetail(
                            tooth.number
                        )
                    )
                }
            }
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

                if (state.value.isSaving) return

                val tooth = state.value.teeth.find {
                    it.number == event.toothNumber
                }

                _state.update {
                    it.copy(
                        selectedTooth = tooth,
                        editingStatus = tooth?.status?.takeIf { status ->
                            status != ToothStatus.UNREGISTERED
                        },
                        editingTreatment = tooth?.treatment.orEmpty(),
                        saveMessage = null
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
                it.copy(isLoading = true)
            }

            try {
                val savedTeeth = getOdontogramUseCase(userId)

                val teethByNumber = savedTeeth.associateBy {
                    it.number
                }

                val allNumbers =
                    UPPER_TOOTH_NUMBERS + LOWER_TOOTH_NUMBERS

                val allTeeth = allNumbers.map { number ->

                    teethByNumber[number] ?: Tooth(
                        number = number,
                        status = ToothStatus.UNREGISTERED
                    )
                }

                _state.update {
                    it.copy(
                        teeth = allTeeth,
                        selectedTooth = null,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                println("ODONTOGRAM_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
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
                                    "${result.insertedCount} nuevos guardados en Room",
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
            loadOdontogram()
        }
    }
    private fun saveSelectedTooth() {

        val userId = currentUserId ?: return
        val currentState = state.value

        val tooth = currentState.selectedTooth ?: return
        val newStatus = currentState.editingStatus ?: return

        if (currentState.isSaving || currentState.isSyncing) {
            return
        }

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isSaving = true,
                    saveMessage = null
                )
            }

            try {
                val updatedTooth = tooth.copy(
                    status = newStatus,
                    treatment = currentState.editingTreatment
                )

                saveToothUseCase(
                    userId = userId,
                    tooth = updatedTooth
                )

                // Consultar nuevamente Room
                val savedTooth = getOdontogramUseCase(userId)
                    .first { it.number == tooth.number }

                _state.update { current ->
                    current.copy(
                        teeth = current.teeth.map {
                            if (it.number == savedTooth.number)
                                savedTooth
                            else
                                it
                        },
                        selectedTooth = savedTooth,
                        editingStatus = savedTooth.status,
                        editingTreatment =
                            savedTooth.treatment.orEmpty(),
                        isSaving = false,
                        saveMessage =
                            "Pieza ${savedTooth.number} guardada correctamente",
                        syncMessage = null
                    )
                }

            } catch (e: Exception) {

                println("SAVE_TOOTH_ERROR: ${e.message}")

                _state.update {
                    it.copy(
                        isSaving = false,
                        saveMessage =
                            "Error al guardar la pieza dental"
                    )
                }
            }
        }
    }
}