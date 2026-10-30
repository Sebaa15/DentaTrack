package org.ucb.dentatrack.Odontogram.Domain.UseCase

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth
import org.ucb.dentatrack.Odontogram.Domain.Model.ToothStatus
import org.ucb.dentatrack.Odontogram.Domain.Model.UPPER_TOOTH_NUMBERS
import org.ucb.dentatrack.Odontogram.Domain.Model.LOWER_TOOTH_NUMBERS
import org.ucb.dentatrack.Odontogram.Domain.Repository.OdontogramRepository

class SaveToothUseCase(
    private val repository: OdontogramRepository
) {

    suspend operator fun invoke(
        userId: String,
        tooth: Tooth
    ) {
        val validNumbers =
            UPPER_TOOTH_NUMBERS + LOWER_TOOTH_NUMBERS

        require(tooth.number in validNumbers) {
            "Número de pieza dental inválido"
        }

        require(tooth.status != ToothStatus.UNREGISTERED) {
            "Seleccione un estado dental"
        }

        repository.saveTooth(
            userId = userId,
            tooth = tooth.copy(
                treatment = tooth.treatment
                    ?.trim()
                    ?.ifBlank { null }
            )
        )
    }
}