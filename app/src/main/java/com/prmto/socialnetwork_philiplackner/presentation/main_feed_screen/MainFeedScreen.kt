package com.prmto.socialnetwork_philiplackner.presentation.main_feed_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.navigation.NavController
import com.prmto.socialnetwork_philiplackner.domain.models.Post
import com.prmto.socialnetwork_philiplackner.presentation.components.Post

@Composable
fun MainFeedScreen(
    navController: NavController
) {
    Post(
        post = Post(
            username = "Tolga Pirim",
            imageUrl = "",
            description = "hi Philip!!! Realy enjoying your videos and I learning a lot from those streammings.",
            profilePictureProfile = "",
            likeCount = 20,
            commentCount = 50
        )
    )
}