package org.ucb.dentatrack.Home.Presentation.Viewmodel

data class HomeState(
    val userId: String? = null,
    val userName: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,

    val totalSteps: Int = 0,
    val completedSteps: Int = 0,
    val planError: String? = null
) {
    val planProgress: Float
        get() = if (totalSteps == 0) {
            0f
        } else {
            completedSteps.toFloat() / totalSteps
        }

    val progressPercentage: Int
        get() = (planProgress * 100).toInt()
}