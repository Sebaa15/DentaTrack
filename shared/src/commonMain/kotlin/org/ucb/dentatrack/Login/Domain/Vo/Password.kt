package org.ucb.dentatrack.Login.Domain.Vo

data class Password(
    val value: String
) {

    fun isBlank(): Boolean {
        return value.isBlank()
    }

    fun hasValidLength(): Boolean {
        return value.length >= 6
    }
}