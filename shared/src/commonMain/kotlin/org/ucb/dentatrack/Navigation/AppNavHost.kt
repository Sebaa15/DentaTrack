package org.ucb.dentatrack.navigation

import edu.ucb.ds.theme.ThemeMode
import org.ucb.dentatrack.Settings.Presentation.SettingsScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.ucb.dentatrack.Login.presentation.Screen.LoginScreen
import org.ucb.dentatrack.Notifications.Presentation.NotificationDetailScreen
import org.ucb.dentatrack.Notifications.Presentation.NotificationHistoryScreen
import org.ucb.dentatrack.Odontogram.Presentation.Screen.OdontogramScreen
import org.ucb.dentatrack.Register.Presentation.Screen.RegisterScreen

@Composable
fun AppNavHost(
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    notificationMessageId: String? = null
) {

    val navController = rememberNavController()

    LaunchedEffect(notificationMessageId) {
        notificationMessageId?.let { id ->
            navController.navigate(
                NavRoute.NotificationDetail(id)
            ) {
                launchSingleTop = true
            }
        }
    }

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
                backStackEntry
                    .toRoute<NavRoute.TreatmentDetail>()

            Text(
                text = "Detalle de la pieza ${route.toothNumber}"
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
        composable<NavRoute.NotificationHistory> {
            NotificationHistoryScreen(
                onOpenDetail = { id ->
                    navController.navigate(
                        NavRoute.NotificationDetail(id)
                    )
                }
            )
        }

        composable<NavRoute.NotificationDetail> { entry ->
            val route = entry.toRoute<NavRoute.NotificationDetail>()

            NotificationDetailScreen(
                messageId = route.messageId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}