package org.ucb.dentatrack.Odontogram.Data.Entity

import androidx.room3.Entity

@Entity(
    tableName = "teeth",
    primaryKeys = ["userId", "number"]
)
data class ToothEntity(
    val userId: Int,
    val number: Int,
    val status: String,
    val treatment: String? = null
)