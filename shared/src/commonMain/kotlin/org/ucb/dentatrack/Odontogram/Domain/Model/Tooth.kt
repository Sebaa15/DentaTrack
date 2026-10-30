package org.ucb.dentatrack.Odontogram.Domain.Model

data class Tooth(
    val number: Int,
    val status: ToothStatus,
    val treatment: String? = null
)

enum class ToothStatus {
    HEALTHY,
    OBSERVATION,
    IN_TREATMENT,
    COMPLETED,
    MISSING,
    UNREGISTERED
}

val UPPER_TOOTH_NUMBERS = listOf(
    18, 17, 16, 15, 14, 13, 12, 11,
    21, 22, 23, 24, 25, 26, 27, 28
)

val LOWER_TOOTH_NUMBERS = listOf(
    48, 47, 46, 45, 44, 43, 42, 41,
    31, 32, 33, 34, 35, 36, 37, 38
)