package org.ucb.dentatrack.Odontogram.Data.Repository

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Model.ToothStatus
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository

class FakeOdontogramRepository: OdontogramRepository{
    override fun getTeeth():List<Tooth>{
        return listOf(
            Tooth(number=18,status=ToothStatus.HEALTHY),
            Tooth(number=17, status = ToothStatus.HEALTHY),
            Tooth(number=16, status = ToothStatus.IN_TREATMENT,treatment="Restauracion"),
            Tooth(number=15, status = ToothStatus.OBSERVATION,treatment="Control"),
            Tooth(number=14, status = ToothStatus.COMPLETED,treatment="Limpieza"),
            Tooth(number=13, status = ToothStatus.HEALTHY),
            Tooth(number=12, status = ToothStatus.HEALTHY),
            Tooth(number=11, status = ToothStatus.HEALTHY),
            )
    }
}