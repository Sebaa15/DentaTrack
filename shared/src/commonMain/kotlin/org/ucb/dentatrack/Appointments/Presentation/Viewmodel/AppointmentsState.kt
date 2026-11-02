package org.ucb.dentatrack.Appointments.Presentation.Viewmodel

import org.ucb.dentatrack.Appointments.Domain.Model.Appointment

data class AppointmentsState(
    val userId: String? = null,
    val appointments: List<Appointment> = emptyList(),

    val editingId: Int = 0,
    val reason: String = "",
    val doctor: String = "",
    val date: String = "",
    val time: String = "",

    val isLoading: Boolean = true,
    val isSaving: Boolean = false,

    val pendingCancelId: Int? = null,
    val message: String? = null,
    val error: String? = null
)