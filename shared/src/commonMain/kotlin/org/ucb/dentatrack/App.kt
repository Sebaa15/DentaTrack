package org.ucb.dentatrack

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.ucb.dentatrack.Login.presentation.Screen.LoginScreen
import org.ucb.dentatrack.Register.Presentation.Screen.RegisterScreen

private enum class AppScreen {
    LOGIN,
    REGISTER,
    ODONTOGRAM
}

@Composable
fun App() {

    var currentScreen by remember {
        mutableStateOf(AppScreen.LOGIN)
    }

    MaterialTheme {

        when (currentScreen) {

            AppScreen.LOGIN -> {

                LoginScreen(
                    onLoginSuccess = {
                        currentScreen = AppScreen.ODONTOGRAM
                    },
                    onRegisterClick = {
                        currentScreen = AppScreen.REGISTER
                    }
                )
            }

            AppScreen.REGISTER -> {

                RegisterScreen(
                    onRegisterSuccess = {
                        currentScreen = AppScreen.LOGIN
                    },
                    onBackToLogin = {
                        currentScreen = AppScreen.LOGIN
                    }
                )
            }

            AppScreen.ODONTOGRAM -> {

                // Próxima pantalla
            }
        }
    }
}