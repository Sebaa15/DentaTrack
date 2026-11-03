package org.ucb.dentatrack.Care.Data.Repository

import org.ucb.dentatrack.Care.Data.Dao.CareDao
import org.ucb.dentatrack.Care.Data.Entity.CareItemEntity
import org.ucb.dentatrack.Care.Data.Entity.CareCheckEntity
import org.ucb.dentatrack.Care.Domain.Model.CareItem
import org.ucb.dentatrack.Care.Domain.Repository.CareRepository

class CareRepositoryImpl(
    private val dao: CareDao
) : CareRepository {

    override suspend fun getDailyCare(
        userId: String,
        date: String
    ): List<CareItem> {

        val id = userId.toInt()
        val items = dao.getItems(id)
        val completedIds =
            dao.getCompletedIds(id, date).toSet()

        return items.map { item ->
            CareItem(
                id = item.id,
                title = item.title,
                note = item.note,
                completedToday = item.id in completedIds
            )
        }
    }

    override suspend fun saveCareItem(
        userId: String,
        title: String,
        note: String
    ) {
        dao.insertItem(
            CareItemEntity(
                userId = userId.toInt(),
                title = title,
                note = note
            )
        )
    }

    override suspend fun setCompleted(
        userId: String,
        careId: Int,
        date: String,
        completed: Boolean
    ) {
        val id = userId.toInt()

        require(dao.countOwnedItem(id, careId) > 0) {
            "Indicación no encontrada"
        }

        if (completed) {
            dao.insertCheck(
                CareCheckEntity(
                    userId = id,
                    careId = careId,
                    date = date
                )
            )
        } else {
            dao.deleteCheck(
                userId = id,
                careId = careId,
                date = date
            )
        }
    }
}