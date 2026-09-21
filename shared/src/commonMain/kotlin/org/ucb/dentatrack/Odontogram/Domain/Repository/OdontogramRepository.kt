package org.ucb.dentatrack.Odontogram.Domain.Repository

import org.ucb.dentatrack.Odontogram.Domain.Model.Tooth

interface OdontogramRepository{
    fun getTeeth(): List<Tooth>
}