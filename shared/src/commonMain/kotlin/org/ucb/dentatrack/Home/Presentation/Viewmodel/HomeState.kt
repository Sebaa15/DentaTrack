package org.ucb.dentatrack.Home.Presentation.Viewmodel

data class HomeState(
    val userId: String? = null,
    val userName: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)