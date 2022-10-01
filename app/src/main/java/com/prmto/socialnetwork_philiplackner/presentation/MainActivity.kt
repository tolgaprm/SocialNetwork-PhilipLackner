package com.prmto.socialnetwork_philiplackner.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.prmto.socialnetwork_philiplackner.presentation.components.StandardScaffold
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SocialNetworkPhilipLacknerTheme
import com.prmto.socialnetwork_philiplackner.presentation.util.Navigation
import com.prmto.socialnetwork_philiplackner.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialNetworkPhilipLacknerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    StandardScaffold(
                        navController = navController,
                        showBottomBar = listOf(
                            Screen.MainFeedScreen.route,
                            Screen.ChatScreen.route,
                            Screen.ActivityScreen.route,
                            Screen.ProfileScreen.route
                        ).any {
                            currentDestination?.route == it
                        },
                        modifier = Modifier.fillMaxSize(),
                        onFabClick = {
                            navController.navigate(Screen.CreatePostScreen.route)
                        }
                    ) {
                        Navigation(navController)
                    }


                }
            }
        }
    }
}

