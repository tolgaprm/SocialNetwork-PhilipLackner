package com.prmto.socialnetwork_philiplackner.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prmto.socialnetwork_philiplackner.presentation.login.LoginScreen
import com.prmto.socialnetwork_philiplackner.presentation.main_feed_screen.MainFeedScreen
import com.prmto.socialnetwork_philiplackner.presentation.register.RegisterScreen
import com.prmto.socialnetwork_philiplackner.presentation.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(navController = navController)
        }
    }
}