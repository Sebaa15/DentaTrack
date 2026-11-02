package org.ucb.dentatrack.Appointments.Presentation.Viewmodel

sealed interface AppointmentsEvent {

    data class Start(
        val userId: String
    ) : AppointmentsEvent

    data class ReasonChanged(
        val value: String
    ) : AppointmentsEvent

    data class DoctorChanged(
        val value: String
    ) : AppointmentsEvent

    data class DateChanged(
        val value: String
    ) : AppointmentsEvent

    data class TimeChanged(
        val value: String
    ) : AppointmentsEvent

    data class EditClicked(
        val id: Int
    ) : AppointmentsEvent

    data class CancelRequested(
        val id: Int
    ) : AppointmentsEvent

    data class CompleteClicked(
        val id: Int
    ) : AppointmentsEvent

    data object CancelConfirmed : AppointmentsEvent
    data object CancelDismissed : AppointmentsEvent
    data object NewClicked : AppointmentsEvent
    data object SaveClicked : AppointmentsEvent
    data object BackClicked : AppointmentsEvent
}