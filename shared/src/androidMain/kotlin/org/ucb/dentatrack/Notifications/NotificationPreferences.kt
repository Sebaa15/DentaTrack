package org.ucb.dentatrack.Notifications

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import java.util.Calendar

object NotificationPreferences {
    fun initialize() {
        config.setDefaultsAsync(
            mapOf(
                "notif_enabled" to true,
                "notif_silent_hours" to "22:00-07:00",
                "notif_frequency" to 1L
            )
        ).addOnCompleteListener {
            config.fetchAndActivate()
        }
    }
    private val config get() = FirebaseRemoteConfig.getInstance()

    fun canShow(lastDisplayed: Long?): Boolean {
        val enabled = config.getBoolean("notif_enabled")
        val hours = config.getString("notif_silent_hours")
        val frequency = config.getLong("notif_frequency").coerceAtLeast(0L)

        if (!enabled || isSilentNow(hours)) return false

        val now = System.currentTimeMillis()

        return lastDisplayed == null ||
                now - lastDisplayed >= frequency * 60_000L
    }

    fun isSilentNow(range: String): Boolean {
        val parts = range.split("-")
        if (parts.size != 2) return false

        fun toMinutes(value: String): Int? {
            val pieces = value.trim().split(":")
            if (pieces.size != 2) return null

            val hour = pieces[0].toIntOrNull() ?: return null
            val minute = pieces[1].toIntOrNull() ?: return null

            if (hour !in 0..23 || minute !in 0..59) return null
            return hour * 60 + minute
        }

        val start = toMinutes(parts[0]) ?: return false
        val end = toMinutes(parts[1]) ?: return false

        val calendar = Calendar.getInstance()
        val current = calendar.get(Calendar.HOUR_OF_DAY) * 60 +
                calendar.get(Calendar.MINUTE)

        return if (start <= end) {
            current >= start && current < end
        } else {
            current >= start || current < end
        }
    }
}