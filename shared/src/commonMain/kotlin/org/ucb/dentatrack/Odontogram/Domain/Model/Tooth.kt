package org.ucb.dentatrack.Odontogram.Domain.Model

data class Tooth(
    val number: Int,
    val status:ToothStatus,
    val treatment: String?=null
)
enum class ToothStatus{
    HEALTHY,OBSERVATION,IN_TREATMENT,COMPLETED
}