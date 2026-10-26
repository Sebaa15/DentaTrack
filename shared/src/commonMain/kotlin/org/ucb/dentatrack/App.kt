package org.ucb.dentatrack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import edu.ucb.ds.theme.DentaTrackTheme
import org.ucb.dentatrack.Settings.Data.SettingsRepository
import org.ucb.dentatrack.navigation.AppNavHost

@Composable
fun App() {
    val repository = remember { SettingsRepository() }

    var themeMode by remember {
        mutableStateOf(repository.getTheme())
    }

    DentaTrackTheme(mode = themeMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavHost(
                themeMode = themeMode,
                onThemeChange = {
                    themeMode = it
                    repository.saveTheme(it)
                }
            )
        }
    }
}