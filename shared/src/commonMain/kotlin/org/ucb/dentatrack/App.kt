package org.ucb.dentatrack

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.ucb.dentatrack.Navigation.AppNavHost

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavHost()
    }
}