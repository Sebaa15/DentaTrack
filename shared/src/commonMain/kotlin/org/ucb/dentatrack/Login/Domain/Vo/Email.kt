package org.ucb.dentatrack.Login.Domain.Vo

data class Email(
    val value: String
) {

    fun isBlank(): Boolean {
        return value.isBlank()
    }

    fun isValid(): Boolean {
        val atPosition = value.indexOf("@")

        if (atPosition <= 0) {
            return false
        }

        val domain = value.substring(atPosition + 1)

        return domain.contains(".")
    }
}