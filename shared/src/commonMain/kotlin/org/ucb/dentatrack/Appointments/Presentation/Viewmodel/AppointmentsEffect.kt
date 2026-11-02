package org.ucb.dentatrack.Appointments.Presentation.Viewmodel

sealed interface AppointmentsEffect {
    data object NavigateBack : AppointmentsEffect
}