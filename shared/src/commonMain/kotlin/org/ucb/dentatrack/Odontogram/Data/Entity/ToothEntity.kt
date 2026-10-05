package org.ucb.dentatrack.Odontogram.Data.Entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(
    tableName = "teeth"
)
data class ToothEntity(

    @PrimaryKey
    val number: Int,

    val status: String,

    val treatment: String? = null
)