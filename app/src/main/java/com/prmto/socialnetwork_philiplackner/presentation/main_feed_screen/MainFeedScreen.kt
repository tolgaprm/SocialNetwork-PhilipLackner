package com.prmto.socialnetwork_philiplackner.presentation.main_feed_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.domain.models.Post
import com.prmto.socialnetwork_philiplackner.presentation.components.Post
import com.prmto.socialnetwork_philiplackner.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.presentation.util.Screen

@Composable
fun MainFeedScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardToolbar(
            navController = navController,
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            showBackArrow = true,
            navActions = {
                IconButton(onClick = {
                    navController.navigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )

        Post(
            post = Post(
                username = "Tolga Pirim",
                imageUrl = "",
                description = "hi Philip!!! Realy enjoying your videos and I learning a lot from those streammings.",
                profilePictureProfile = "",
                likeCount = 20,
                commentCount = 50
            ),
            onPostClick = {
                navController.navigate(Screen.PostDetailScreen.route)
            }
        )
    }


}