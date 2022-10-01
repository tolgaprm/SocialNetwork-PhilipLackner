package com.prmto.socialnetwork_philiplackner.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prmto.socialnetwork_philiplackner.presentation.activity.ActivityScreen
import com.prmto.socialnetwork_philiplackner.presentation.chat.ChatScreen
import com.prmto.socialnetwork_philiplackner.presentation.login.LoginScreen
import com.prmto.socialnetwork_philiplackner.presentation.main_feed_screen.MainFeedScreen
import com.prmto.socialnetwork_philiplackner.presentation.profile.ProfileScreen
import com.prmto.socialnetwork_philiplackner.presentation.register.RegisterScreen
import com.prmto.socialnetwork_philiplackner.presentation.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
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

        composable(Screen.ChatScreen.route) {
            ChatScreen(navController = navController)
        }

        composable(Screen.ActivityScreen.route) {
            ActivityScreen(navController = navController)
        }

        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
    }
}