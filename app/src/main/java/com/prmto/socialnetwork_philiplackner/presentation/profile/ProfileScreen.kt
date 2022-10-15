package com.prmto.socialnetwork_philiplackner.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.prmto.socialnetwork_philiplackner.presentation.util.toPx


@Composable
fun ProfileScreen(
    navController: NavController,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val lazyListState = rememberLazyListState()

    var toolbarOffsetY = viewModel.toolbarOffsetY.value
    var expandedAspectRatio = viewModel.expandedRatio.value

    val iconSizeExpanded = 35.dp

    val toolbarHeightCollapsed = 75.dp

    val imageCollapsedOffset = remember {
        (toolbarHeightCollapsed - profilePictureSize / 2f) / 2f
    }

    val iconCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - iconSizeExpanded) / 2f
    }

    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp

    val iconHorizontalCenterLength = bannerHeight.toPx() / 2f -
            (profilePictureSize / 2f).toPx() +
            SpaceSmall.toPx()

    val toolbarHeightExpanded = remember {
        bannerHeight + profilePictureSize
    }

    val maxOffset = remember {
        toolbarHeightExpanded - toolbarHeightCollapsed
    }


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if (delta > 0 && lazyListState.firstVisibleItemIndex != 0) {
                    return Offset.Zero
                }
                val newOffset = toolbarOffsetY + delta
                toolbarOffsetY = newOffset.coerceIn(
                    minimumValue = -maxOffset.toPx(),
                    maximumValue = 0f
                )
                expandedAspectRatio = ((toolbarOffsetY + maxOffset.toPx()) / maxOffset.toPx())
                return Offset.Zero
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
            state = lazyListState
        ) {

            item {
                Spacer(
                    modifier = Modifier.height(
                        toolbarHeightExpanded - profilePictureSize / 2f
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
                .align(Alignment.TopCenter)
        ) {

            BannerSection(
                modifier = Modifier
                    .height(
                        (bannerHeight * expandedAspectRatio).coerceIn(
                            minimumValue = toolbarHeightCollapsed,
                            maximumValue = bannerHeight
                        )
                    ),
                leftIconModifier = Modifier.graphicsLayer {
                    translationY = (1f - expandedAspectRatio) *
                            -iconCollapsedOffsetY.toPx()
                    translationX = (1f - expandedAspectRatio) * iconHorizontalCenterLength
                },
                rightIconModifier = Modifier.graphicsLayer {
                    translationY = (1f - expandedAspectRatio) *
                            -iconCollapsedOffsetY.toPx()
                    translationX = (1f - expandedAspectRatio) * -iconHorizontalCenterLength
                }
            )
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = stringResource(id = R.string.profile_picture),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .graphicsLayer {
                        translationY = -profilePictureSize.toPx() / 2f -
                                (1 - expandedAspectRatio) * imageCollapsedOffset.toPx()
                        transformOrigin = TransformOrigin(
                            pivotFractionX = 0.5f,
                            pivotFractionY = 0f
                        )
                        val scale = 0.5f + expandedAspectRatio * 0.5f
                        scaleX = scale
                        scaleY = scale
                    }
                    .size(profilePictureSize)
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