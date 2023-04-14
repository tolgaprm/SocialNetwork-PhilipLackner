package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile

import android.util.Base64
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.presentation.components.Post
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceSmall
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.presentation.util.asString
import com.prmto.socialnetwork_philiplackner.core.util.Screen
import com.prmto.socialnetwork_philiplackner.core.util.sendSharePostIntent
import com.prmto.socialnetwork_philiplackner.core.util.toPx
import com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile.components.BannerSection
import com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile.components.ProfileHeaderSection
import kotlinx.coroutines.flow.collectLatest


@Composable
@ExperimentalCoilApi
fun ProfileScreen(
    scaffoldState: ScaffoldState,
    userId: String? = null,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit,
) {

    val pagingState = viewModel.pagingState.value

    val lazyListState = rememberLazyListState()
    val toolbarState = viewModel.toolbarState.value
    val iconSizeExpanded = 35.dp
    val toolbarHeightCollapsed = 75.dp
    val imageCollapsedOffsetY = remember {
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
    val context = LocalContext.current
    val state = viewModel.state.value


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val shouldNotScroll =
                    delta > 0 && lazyListState.firstVisibleItemIndex != 0 || viewModel.pagingState.value.items.isEmpty()
                if (shouldNotScroll) {
                    return Offset.Zero
                }
                val newOffset = viewModel.toolbarState.value.toolbarOffsetY + delta
                viewModel.setToolbarOffsetY(
                    newOffset.coerceIn(
                        minimumValue = -maxOffset.toPx(),
                        maximumValue = 0f
                    )
                )
                viewModel.setExpandedRatio(((viewModel.toolbarState.value.toolbarOffsetY + maxOffset.toPx()) / maxOffset.toPx()))
                return Offset.Zero
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getProfile(userId = userId)
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
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
            contentPadding = PaddingValues(bottom = 90.dp),
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
                state.profile?.let { profile ->
                    ProfileHeaderSection(
                        user = profile.toUser(),
                        isOwnProfile = profile.isOwnProfile,
                        isFollowing = profile.isFollowing,
                        onEditClick = {
                            onNavigate(
                                Screen.EditProfileScreen.route + "/${profile.userId}"
                            )
                        },
                        onLogoutClick = {
                            viewModel.onEvent(ProfileEvent.ShowLogoutDialog)
                        },
                        onMessageClick = {
                            val encodedProfilePictureUrl = Base64.encodeToString(profile.profilePictureUrl.encodeToByteArray(), 0)
                            onNavigate(
                                Screen.MessagesScreen.route + "/${profile.userId}/${profile.username}/$encodedProfilePictureUrl"
                            )
                        }
                    )
                }
            }
            itemsIndexed(pagingState.items) { i, post ->
                if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                    viewModel.loadNextPosts()
                }
                Post(
                    post = post,
                    onPostClick = {
                        onNavigate(Screen.PostDetailScreen.route + "/${post.id}")
                    },
                    onLikeClick = {
                        viewModel.onEvent(
                            ProfileEvent.LikedPost(
                                post.id
                            )
                        )
                    },
                    onCommentClick = {
                        onNavigate(Screen.PostDetailScreen.route + "/${post.id}?shouldShowKeyboard=${true}")
                    },
                    onShareClick = {
                        context.sendSharePostIntent(postId = post.id)
                    },
                    showProfileImage = false
                )

            }

        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            state.profile?.let { profile ->
                BannerSection(
                    modifier = Modifier
                        .height(
                            (bannerHeight * toolbarState.expandedRatio).coerceIn(
                                minimumValue = toolbarHeightCollapsed,
                                maximumValue = bannerHeight
                            )
                        ),
                    leftIconModifier = Modifier.graphicsLayer {
                        translationY = (1f - toolbarState.expandedRatio) *
                                -iconCollapsedOffsetY.toPx()
                        translationX =
                            (1f - toolbarState.expandedRatio) * iconHorizontalCenterLength
                    },
                    rightIconModifier = Modifier.graphicsLayer {
                        translationY = (1f - toolbarState.expandedRatio) *
                                -iconCollapsedOffsetY.toPx()
                        translationX =
                            (1f - toolbarState.expandedRatio) * -iconHorizontalCenterLength
                    },
                    bannerUrl = profile.bannerUrl,
                    topSkills = profile.topSkills,
                    shouldShowGitHub = !profile.gitHubUrl.isNullOrBlank(),
                    shouldShowInstagram = !profile.instagramUrl.isNullOrBlank(),
                    shouldShowLinkedInUrl = !profile.linkedInUrl.isNullOrBlank()
                )

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(profile.profilePictureUrl)
                        .build(),
                    contentDescription = stringResource(id = R.string.profile_picture),
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .graphicsLayer {
                            translationY = -profilePictureSize.toPx() / 2f -
                                    (1 - toolbarState.expandedRatio) * imageCollapsedOffsetY.toPx()
                            transformOrigin = TransformOrigin(
                                pivotFractionX = 0.5f,
                                pivotFractionY = 0f
                            )
                            val scale = 0.5f + toolbarState.expandedRatio * 0.5f
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
        if (state.isLogoutDialogVisible) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                },
                modifier = Modifier.align(Center),
                title = {
                    Text(text = stringResource(id = R.string.do_you_want_to_logout))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.onEvent(ProfileEvent.Logout)
                            viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                            onLogout()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.6f)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.yes).uppercase(),
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                    }) {
                        Text(
                            text = stringResource(id = R.string.no).uppercase(),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    }
}