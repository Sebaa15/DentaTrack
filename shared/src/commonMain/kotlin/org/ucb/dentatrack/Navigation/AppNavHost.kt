package org.ucb.dentatrack.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ucb.dentatrack.Login.presentation.Screen.LoginScreen
import org.ucb.dentatrack.Register.Presentation.Screen.RegisterScreen
import org.ucb.dentatrack.navigation.NavRoute

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login
    ) {

        composable<NavRoute.Login> {

            LoginScreen(
               navController= navController)
        }

        composable<NavRoute.Register> {

            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(
                        NavRoute.Login
                    )
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}