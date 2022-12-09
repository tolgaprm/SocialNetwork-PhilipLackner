package com.prmto.socialnetwork_philiplackner.core.presentation.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.domain.util.Screen
import com.prmto.socialnetwork_philiplackner.feature_activity.presentation.activity.ActivityScreen
import com.prmto.socialnetwork_philiplackner.feature_auth.presantation.login.LoginScreen
import com.prmto.socialnetwork_philiplackner.feature_auth.presantation.register.RegisterScreen
import com.prmto.socialnetwork_philiplackner.feature_chat.presentation.chat.ChatScreen
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.create_post.CreatePostScreen
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.main_feed.MainFeedScreen
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list.PersonListScreen
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.post_detail.PostDetailScreen
import com.prmto.socialnetwork_philiplackner.feature_profile.presantation.edit_profile.EditProfileScreen
import com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile.ProfileScreen
import com.prmto.socialnetwork_philiplackner.feature_profile.presantation.search.SearchScreen
import com.prmto.socialnetwork_philiplackner.feature_splash.presantation.splash.SplashScreen

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

        composable(Screen.PersonListScreen.route) {
            PersonListScreen(navController = navController)
        }
    }
}