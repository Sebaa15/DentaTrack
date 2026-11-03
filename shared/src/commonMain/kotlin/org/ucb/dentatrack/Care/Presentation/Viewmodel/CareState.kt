package org.ucb.dentatrack.Care.Presentation.Viewmodel

import org.ucb.dentatrack.Care.Domain.Model.CareItem
import org.ucb.dentatrack.Appointments.Domain.Model.Appointment

data class CareState(
    val userId: String? = null,
    val date: String = "",
    val items: List<CareItem> = emptyList(),

    val currentTreatment: String? = null,
    val nextAppointment: Appointment? = null,

    val newTitle: String = "",
    val newNote: String = "",

    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val error: String? = null,
    val message: String? = null
) {
    val completedCount: Int
        get() = items.count { it.completedToday }

    val progress: Float
        get() = if (items.isEmpty()) {
            0f
        } else {
            completedCount.toFloat() / items.size
        }
}