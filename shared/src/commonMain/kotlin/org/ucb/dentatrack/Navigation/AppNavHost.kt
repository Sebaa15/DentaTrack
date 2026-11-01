package org.ucb.dentatrack.navigation

import org.ucb.dentatrack.Home.Presentation.Screen.HomeScreen
import edu.ucb.ds.theme.ThemeMode
import org.ucb.dentatrack.Settings.Presentation.SettingsScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.ucb.dentatrack.Login.presentation.Screen.LoginScreen
import org.ucb.dentatrack.Odontogram.Presentation.Screen.OdontogramScreen
import org.ucb.dentatrack.Register.Presentation.Screen.RegisterScreen
import org.ucb.dentatrack.TreatmentDetail.Presentation.Screen.TreatmentDetailScreen
import org.ucb.dentatrack.TreatmentPlan.Presentation.Screen.TreatmentPlanScreen

@Composable
fun AppNavHost(
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login
    ) {

        composable<NavRoute.Login> {

            LoginScreen(
               navController= navController,
                onOpenSettings = {
                    navController.navigate(NavRoute.Settings)
                })
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
                backStackEntry ->

            val route =
                backStackEntry
                    .toRoute<NavRoute.Odontogram>()

            OdontogramScreen(
                navController = navController,
                userId = route.userId,
                onOpenSettings = {
                    navController.navigate(NavRoute.Settings)
                }
            )
        }
        composable<NavRoute.TreatmentDetail> { backStackEntry ->

            val route =
                backStackEntry.toRoute<NavRoute.TreatmentDetail>()

            TreatmentDetailScreen(
                userId = route.userId,
                toothNumber = route.toothNumber,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<NavRoute.Settings> {
            SettingsScreen(
                themeMode = themeMode,
                onThemeChange = onThemeChange,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<NavRoute.Home> { backStackEntry ->

            val route = backStackEntry.toRoute<NavRoute.Home>()

            HomeScreen(
                navController = navController,
                userId = route.userId,
                onOpenSettings = {
                    navController.navigate(NavRoute.Settings)
                }
            )
        }
        composable<NavRoute.TreatmentPlan> { backStackEntry ->

            val route =
                backStackEntry.toRoute<NavRoute.TreatmentPlan>()

            TreatmentPlanScreen(
                userId = route.userId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}