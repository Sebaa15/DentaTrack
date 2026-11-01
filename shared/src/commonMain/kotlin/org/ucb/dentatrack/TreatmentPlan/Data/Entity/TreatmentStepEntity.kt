package org.ucb.dentatrack.TreatmentPlan.Data.Entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "treatment_steps")
data class TreatmentStepEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: Int,
    val title: String,
    val date: String,
    val note: String,
    val status: String
)