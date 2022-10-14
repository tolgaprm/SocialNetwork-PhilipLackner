package com.prmto.socialnetwork_philiplackner.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.domain.models.Post
import com.prmto.socialnetwork_philiplackner.domain.models.User
import com.prmto.socialnetwork_philiplackner.presentation.components.Post
import com.prmto.socialnetwork_philiplackner.presentation.profile.components.BannerSection
import com.prmto.socialnetwork_philiplackner.presentation.profile.components.ProfileHeaderSection
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.ProfilePictureSizeLarge
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceSmall
import com.prmto.socialnetwork_philiplackner.presentation.util.Screen


@Composable
fun ProfileScreen(navController: NavController) {


    val toolbarOffsetY by remember {
        mutableStateOf(0f)
    }

    val toolbarHeightCollapsed = 56.dp
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    val toolbarHeightExpanded = remember {
        bannerHeight + ProfilePictureSizeLarge
    }


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetY + delta

                return super.onPreScroll(available, source)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {

            item {
                Spacer(
                    modifier = Modifier.height(
                        toolbarHeightExpanded - ProfilePictureSizeLarge / 2f
                    )
                )
            }
            item {
                ProfileHeaderSection(
                    user = User(
                        profilePictureUrl = "",
                        username = "Tolga Pirim",
                        description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed\n" +
                                "diam nonumy eirmod tempor invidunt ut labore et dolore \n" +
                                "magna aliquyam erat, sed diam voluptua",
                        followerCount = 1455,
                        followingCount = 25,
                        postCount = 54
                    )
                )
            }
            items(20) {
                Spacer(modifier = Modifier.height(SpaceSmall))
                Post(
                    post = Post(
                        username = "Tolga Pirim",
                        imageUrl = "",
                        description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed\n" +
                                "diam nonumy eirmod tempor invidunt ut labore et dolore \n" +
                                "magna aliquyam erat, sed diam voluptua",
                        profilePictureProfile = "",
                        likeCount = 20,
                        commentCount = 50
                    ),
                    onPostClick = {
                        navController.navigate(Screen.PostDetailScreen.route)
                    },
                    showProfileImage = false
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {

            BannerSection(modifier = Modifier.height(bannerHeight))
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = stringResource(id = R.string.profile_picture),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .graphicsLayer {
                        translationY = -ProfilePictureSizeLarge.toPx() / 2f
                    }
                    .size(ProfilePictureSizeLarge)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onSurface,
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
        }


    }


}