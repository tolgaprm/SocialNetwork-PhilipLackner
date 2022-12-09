package com.prmto.socialnetwork_philiplackner.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.prmto.socialnetwork_philiplackner.core.presentation.components.Navigation
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardScaffold
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SocialNetworkPhilipLacknerTheme
import com.prmto.socialnetwork_philiplackner.core.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
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
                    val scaffoldState = rememberScaffoldState()
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
                        scaffoldState = scaffoldState,
                        modifier = Modifier.fillMaxSize(),
                        onFabClick = {
                            navController.navigate(Screen.CreatePostScreen.route)
                        }
                    ) {
                        Navigation(navController,scaffoldState)
                    }


                }
            }
        }
    }
}

