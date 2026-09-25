package org.ucb.dentatrack.Odontogram.Presentation.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Model.ToothStatus
import org.ucb.dentatrack.Odontogram.Presentation.Viewmodel.OdontogramState


@Composable
fun OdontogramContent(state: OdontogramState,onToothSelected: (Int)-> Unit) {
    val scrollState= rememberScrollState()
    Column(
        modifier= Modifier.fillMaxSize().verticalScroll(scrollState).
        padding(16.dp)
    ){
        Text(
            text="MI ODONTOGRAMA",
            style=MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier=Modifier.height(8.dp)
        )
        Text(
            text="Seleccione una pieza dental para ver su tratamiento"
        )
        Spacer(
            modifier=Modifier.height(24.dp)
        )
        if(state.teeth.isEmpty()) {
            Text( text="No existen placas registradas")
        }else{
            FlowRow(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 4
            ){
                state.teeth.forEach{tooth->
                    ToothCard(tooth=tooth,onClick={
                        onToothSelected(tooth.number)
                    })
                }
            }
            }
        state.selectedTooth?.let{tooth->
            Spacer(
                modifier=Modifier.height(24.dp)
            )
            Text(text="Pieza seleccionada: ${tooth.number}",style= MaterialTheme.typography.titleMedium)
            Text(text="Estado: ${getStatusText(tooth.status)}")
            tooth.treatment?.let{treatment->
                Text(text="Tratamiento: ${treatment}")
            }
        }
    }
}

@Composable
private fun ToothCard(tooth: Tooth,onClick:()->Unit){
    Card(
        onClick=onClick,
        modifier=Modifier.height(78.dp)
    ){
        Column(
            modifier=Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text=tooth.number.toString(),
                style=MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center)
            Text(text=getStatusShortText(tooth.status),
                style=MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center)
        }
    }
}

private fun getStatusText(status: ToothStatus):String{
    return when(status) {
        ToothStatus.HEALTHY -> "Saludable"
        ToothStatus.OBSERVATION -> "En observacion"
        ToothStatus.IN_TREATMENT -> "En tratamiento"
        ToothStatus.COMPLETED -> "Completado el tratamiento"
    }
}
private fun getStatusShortText(status: ToothStatus):String{
    return when(status){
        ToothStatus.HEALTHY -> "Sano"
        ToothStatus.OBSERVATION -> "Revision"
        ToothStatus.IN_TREATMENT -> "Tratamiento"
        ToothStatus.COMPLETED -> "Finalizado"
    }
}