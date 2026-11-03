package org.ucb.dentatrack.Care.Data.Dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

import org.ucb.dentatrack.Care.Data.Entity.CareItemEntity
import org.ucb.dentatrack.Care.Data.Entity.CareCheckEntity

@Dao
interface CareDao {

    @Query("""
        SELECT * FROM care_items
        WHERE userId = :userId
        ORDER BY id ASC
    """)
    suspend fun getItems(
        userId: Int
    ): List<CareItemEntity>

    @Insert
    suspend fun insertItem(
        item: CareItemEntity
    ): Long

    @Query("""
        SELECT careId FROM care_checks
        WHERE userId = :userId
        AND date = :date
    """)
    suspend fun getCompletedIds(
        userId: Int,
        date: String
    ): List<Int>

    @Query("""
        SELECT COUNT(*) FROM care_items
        WHERE id = :careId
        AND userId = :userId
    """)
    suspend fun countOwnedItem(
        userId: Int,
        careId: Int
    ): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCheck(
        check: CareCheckEntity
    ): Long

    @Query("""
        DELETE FROM care_checks
        WHERE userId = :userId
        AND careId = :careId
        AND date = :date
    """)
    suspend fun deleteCheck(
        userId: Int,
        careId: Int,
        date: String
    )
}