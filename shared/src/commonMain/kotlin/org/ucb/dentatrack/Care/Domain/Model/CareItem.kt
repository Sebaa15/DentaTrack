package org.ucb.dentatrack.Care.Domain.Model

data class CareItem(
    val id: Int,
    val title: String,
    val note: String = "",
    val completedToday: Boolean = false
)