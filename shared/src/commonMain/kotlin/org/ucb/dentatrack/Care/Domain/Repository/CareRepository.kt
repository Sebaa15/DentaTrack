package org.ucb.dentatrack.Care.Domain.Repository

import org.ucb.dentatrack.Care.Domain.Model.CareItem

interface CareRepository {

    suspend fun getDailyCare(
        userId: String,
        date: String
    ): List<CareItem>

    suspend fun saveCareItem(
        userId: String,
        title: String,
        note: String
    )

    suspend fun setCompleted(
        userId: String,
        careId: Int,
        date: String,
        completed: Boolean
    )
}