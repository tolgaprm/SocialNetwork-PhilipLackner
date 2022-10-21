package com.prmto.socialnetwork_philiplackner.presentation.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prmto.socialnetwork_philiplackner.domain.models.Post
import com.prmto.socialnetwork_philiplackner.presentation.activity.ActivityScreen
import com.prmto.socialnetwork_philiplackner.presentation.chat.ChatScreen
import com.prmto.socialnetwork_philiplackner.presentation.create_post.CreatePostScreen
import com.prmto.socialnetwork_philiplackner.presentation.edit_profile.EditProfileScreen
import com.prmto.socialnetwork_philiplackner.presentation.login.LoginScreen
import com.prmto.socialnetwork_philiplackner.presentation.main_feed_screen.MainFeedScreen
import com.prmto.socialnetwork_philiplackner.presentation.post_detail.PostDetailScreen
import com.prmto.socialnetwork_philiplackner.presentation.profile.ProfileScreen
import com.prmto.socialnetwork_philiplackner.presentation.register.RegisterScreen
import com.prmto.socialnetwork_philiplackner.presentation.search.SearchScreen
import com.prmto.socialnetwork_philiplackner.presentation.splash.SplashScreen

@ExperimentalMaterialApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
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
        composable(Screen.CreatePostScreen.route) {
            CreatePostScreen(navController = navController)
        }
        composable(Screen.PostDetailScreen.route) {
            PostDetailScreen(
                navController = navController,
                post = Post(
                    username = "Tolga Pirim",
                    imageUrl = "",
                    description = "Lorem ipsum dolor sit amet, consetetur, asdfadsf" + "diam nonumy eirmod tempor invidunt ut fda fdsa" +
                            "magna aliquyam erat, sed diam voluptua",
                    profilePictureProfile = "",
                    likeCount = 20,
                    commentCount = 50
                )
            )
        }

        composable(Screen.EditProfileScreen.route) {
            EditProfileScreen(navController = navController)
        }

        composable(Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
    }
}