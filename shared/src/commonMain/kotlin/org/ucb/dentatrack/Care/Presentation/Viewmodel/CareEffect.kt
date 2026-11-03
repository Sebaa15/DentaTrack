package org.ucb.dentatrack.Care.Presentation.Viewmodel

sealed interface CareEffect {
    data object NavigateBack : CareEffect
}