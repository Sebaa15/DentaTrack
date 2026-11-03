package org.ucb.dentatrack.Care.Data.Entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "care_items")
data class CareItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val title: String,
    val note: String
)