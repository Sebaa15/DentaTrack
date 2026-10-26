package org.ucb.dentatrack.Notifications.Data.Dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow
import org.ucb.dentatrack.Notifications.Data.Entity.NotificationEntity

@Dao
interface NotificationDao {

    @Upsert
    suspend fun save(item: NotificationEntity)

    @Query("SELECT * FROM notification_history ORDER BY receivedAt DESC")
    fun observeAll(): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notification_history WHERE messageId = :id LIMIT 1")
    fun observeById(id: String): Flow<NotificationEntity?>

    @Query("SELECT * FROM notification_history WHERE messageId = :id LIMIT 1")
    suspend fun findById(id: String): NotificationEntity?

    @Query("SELECT * FROM notification_history WHERE displayed = 0 ORDER BY receivedAt ASC")
    suspend fun pending(): List<NotificationEntity>

    @Query("SELECT MAX(displayedAt) FROM notification_history")
    suspend fun lastDisplayedAt(): Long?

    @Query("UPDATE notification_history SET displayed = 1, displayedAt = :time WHERE messageId = :id")
    suspend fun markDisplayed(id: String, time: Long)
}