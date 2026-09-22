package org.ucb.dentatrack.Odontogram.Presentation.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.dentatrack.Odontogram.Domain.UseCase.GetOdontogramUseCase


class OdontogramViewModel(private val getOdontogramUseCase: GetOdontogramUseCase): ViewModel()
{
    private val _state=MutableStateFlow(OdontogramState())
    val state=_state.asStateFlow()
    private val _effect=MutableSharedFlow<OdontogramEffect>()
    val effect=_effect.asSharedFlow()

    fun emitEvent(event:OdontogramEvent)
    {
        when(event){
            OdontogramEvent.LoadOdontogram->{
                loadOdontogram()
            }
            is OdontogramEvent.ToothSelected-> {
                val tooth = state.value.teeth.find {
                    it.number == event.toothNumber
                }
                _state.update {
                    it.copy(selectedTooth = tooth)
                }
                if (tooth != null) {
                    emitEffect(OdontogramEffect.NavigateToTreatmentDetail
                        (toothNumber = tooth.number))
                }
            }
        }
    }
    private fun loadOdontogram(){
        val teeth=getOdontogramUseCase()
        _state.update{
            it.copy(teeth=teeth,isLoading=false)
        }
    }
    private fun emitEffect(
        effect:OdontogramEffect
    )=
        viewModelScope.launch{
            _effect.emit(effect)
        }
}