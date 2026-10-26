package org.ucb.dentatrack.Notifications.Data.Entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "notification_history")
data class NotificationEntity(
    @PrimaryKey val messageId: String,
    val title: String,
    val body: String,
    val receivedAt: Long,
    val displayed: Boolean = false,
    val displayedAt: Long? = null
)