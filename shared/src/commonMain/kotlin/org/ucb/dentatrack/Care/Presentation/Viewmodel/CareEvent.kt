package org.ucb.dentatrack.Care.Presentation.Viewmodel

sealed interface CareEvent {

    data class Start(
        val userId: String,
        val date: String
    ) : CareEvent

    data class TitleChanged(
        val value: String
    ) : CareEvent

    data class NoteChanged(
        val value: String
    ) : CareEvent

    data class CheckedChanged(
        val careId: Int,
        val completed: Boolean
    ) : CareEvent

    data object AddClicked : CareEvent
    data object BackClicked : CareEvent
}