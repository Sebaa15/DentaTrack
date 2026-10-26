package org.ucb.dentatrack.Odontogram.Data.Mapper

import org.ucb.dentatrack.Odontogram.Data.Entity.ToothEntity
import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Model.ToothStatus

fun ToothEntity.toDomain():
        Tooth {

    return Tooth(
        number = number,
        status =
            ToothStatus.valueOf(status),
        treatment = treatment
    )
}
