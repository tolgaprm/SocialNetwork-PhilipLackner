package com.prmto.socialnetwork_philiplackner.core.presentation.components

import android.content.Intent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil.annotation.ExperimentalCoilApi
import com.prmto.socialnetwork_philiplackner.core.util.Screen
import com.prmto.socialnetwork_philiplackner.feature_activity.presentation.ActivityScreen
import com.prmto.socialnetwork_philiplackner.feature_auth.presantation.login.LoginScreen
import com.prmto.socialnetwork_philiplackner.feature_auth.presantation.register.RegisterScreen
import com.prmto.socialnetwork_philiplackner.feature_auth.presantation.splash.SplashScreen
import com.prmto.socialnetwork_philiplackner.feature_chat.presentation.chat.ChatScreen
import com.prmto.socialnetwork_philiplackner.feature_chat.presentation.message.MessageScreen
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.create_post.CreatePostScreen
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.main_feed.MainFeedScreen
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list.PersonListScreen
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.post_detail.PostDetailScreen
import com.prmto.socialnetwork_philiplackner.feature_profile.presantation.edit_profile.EditProfileScreen
import com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile.ProfileScreen
import com.prmto.socialnetwork_philiplackner.feature_profile.presantation.search.SearchScreen
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onPopBackStack = { navController.popBackStack() },
                onNavigate = navController::navigate
            )
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigate = {
                    navController.popBackStack()
                    navController.navigate(it)
                },
                scaffoldState = scaffoldState
            )
        }

        composable(Screen.RegisterScreen.route) {
            RegisterScreen(
                scaffoldState = scaffoldState,
                onPopStack = { navController.popBackStack() }
            )
        }

        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(
                scaffoldState = scaffoldState,
                onNavigate = navController::navigate,
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(Screen.ChatScreen.route) {
            ChatScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }

        composable(
            route = Screen.MessagesScreen.route + "/{remoteUserId}/{remoteUsername}/{remoteUserProfilePictureUrl}?chatId={chatId}",
            arguments = listOf(
                navArgument("chatId") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("remoteUserId") {
                    type = NavType.StringType
                },
                navArgument("remoteUsername") {
                    type = NavType.StringType
                },
                navArgument("remoteUserProfilePictureUrl") {
                    type = NavType.StringType
                },
            )
        ) {
            val remoteUserName = it.arguments?.getString("remoteUsername")!!
            val remoteUserProfilePictureUrl =
                it.arguments?.getString("remoteUserProfilePictureUrl")!!
            val remoteUserId = it.arguments?.getString("remoteUserId")!!
            MessageScreen(
                remoteUsername = remoteUserName,
                encodedRemoteUserProfilePictureUrl = remoteUserProfilePictureUrl,
                remoteUserId = remoteUserId,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }

        composable(Screen.ActivityScreen.route) {
            ActivityScreen(
                onNavigateUp = { navController.navigateUp() },
                onNavigate = navController::navigate
            )
        }

        composable(
            route = Screen.ProfileScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null

                }
            )
        ) {
            ProfileScreen(
                userId = it.arguments?.getString("userId"),
                scaffoldState = scaffoldState,
                onNavigate = navController::navigate,
                onLogout = {
                    navController.navigate(Screen.LoginScreen.route)
                }
            )
        }
        composable(Screen.CreatePostScreen.route) {
            CreatePostScreen(
                scaffoldState = scaffoldState,
                onNavigateUp = { navController.navigateUp() },
                onPopBackStack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.PostDetailScreen.route + "/{postId}?shouldShowKeyboard={shouldShowKeyboard}",
            arguments = listOf(
                navArgument(
                    name = "postId"
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = "shouldShowKeyboard"
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = "https://prmcoding.com/{postId}"
                }
            )

        ) {
            val shouldShowKeyboard = it.arguments?.getBoolean("shouldShowKeyboard")
            PostDetailScreen(
                scaffoldState = scaffoldState,
                shouldShowKeyboard = shouldShowKeyboard ?: false,
                onNavigateUp = { navController.navigateUp() },
                onNavigate = navController::navigate
            )
        }

        composable(
            route = Screen.EditProfileScreen.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                }
            )
        ) {
            EditProfileScreen(
                scaffoldState = scaffoldState,
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(Screen.SearchScreen.route) {
            SearchScreen(
                scaffoldState = scaffoldState,
                onNavigate = navController::navigate,
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.PersonListScreen.route + "/{parentId}",
            arguments = listOf(
                navArgument("parentId") {
                    type = NavType.StringType
                }
            )
        ) {
            PersonListScreen(
                scaffoldState = scaffoldState,
                onNavigateUp = { navController.navigateUp() },
                onNavigate = navController::navigate
            )
        }
    }
}