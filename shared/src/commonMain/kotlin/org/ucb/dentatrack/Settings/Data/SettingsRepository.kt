package org.ucb.dentatrack.Settings.Data

import com.russhwolf.settings.Settings
import edu.ucb.ds.theme.ThemeMode

class SettingsRepository {

    private val settings: Settings = Settings()

    fun getTheme(): ThemeMode {
        val saved = settings.getString(
            "theme_mode",
            ThemeMode.SYSTEM.name
        )

        return ThemeMode.entries.firstOrNull {
            it.name == saved
        } ?: ThemeMode.SYSTEM
    }

    fun saveTheme(mode: ThemeMode) {
        settings.putString("theme_mode", mode.name)
    }
}