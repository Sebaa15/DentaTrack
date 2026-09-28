package org.ucb.dentatrack.navigation

import org.ucb.dentatrack.Movies.presentation.screen.CatalogScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.ucb.dentatrack.Crypto.presentation.screen.CryptoScreen
import org.ucb.dentatrack.Login.presentation.Screen.LoginScreen
import org.ucb.dentatrack.Odontogram.Presentation.Screen.OdontogramScreen
import org.ucb.dentatrack.Register.Presentation.Screen.RegisterScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Crypto
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
        composable<NavRoute.Odontogram> {
            OdontogramScreen(
                navController= navController
            )
        }
        composable<NavRoute.TreatmentDetail> { backStackEntry ->

            val route =
                backStackEntry
                    .toRoute<NavRoute.TreatmentDetail>()

            Text(
                text = "Detalle de la pieza ${route.toothNumber}"
            )
        }
        composable<NavRoute.Catalog> {
            CatalogScreen()
        }
        composable<NavRoute.Crypto>{
            CryptoScreen()
        }
    }
}