package org.ucb.dentatrack.Odontogram.Domain.Repository

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth

interface OdontogramRepository{
    suspend fun getTeeth(): List<Tooth>
}