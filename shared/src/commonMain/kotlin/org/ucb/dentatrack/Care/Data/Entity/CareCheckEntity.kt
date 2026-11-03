package org.ucb.dentatrack.Care.Data.Entity

import androidx.room3.Entity

@Entity(
    tableName = "care_checks",
    primaryKeys = ["userId", "careId", "date"]
)
data class CareCheckEntity(
    val userId: Int,
    val careId: Int,
    val date: String
)