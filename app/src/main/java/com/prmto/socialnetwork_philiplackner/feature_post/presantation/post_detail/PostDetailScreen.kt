package com.prmto.socialnetwork_philiplackner.feature_post.presantation.post_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.presentation.components.ActionRow
import com.prmto.socialnetwork_philiplackner.core.presentation.components.SendTextField
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardTextField
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.*
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.presentation.util.asString
import com.prmto.socialnetwork_philiplackner.core.util.Screen
import com.prmto.socialnetwork_philiplackner.core.util.sendSharePostIntent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailScreen(
    scaffoldState: ScaffoldState,
    viewModel: PostDetailViewModel = hiltViewModel(),
    shouldShowKeyboard: Boolean,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit,
) {

    val state = viewModel.state.value
    val commentTextFieldState = viewModel.commentTextFieldState.value
    val commentState = viewModel.commentState.value

    val context = LocalContext.current

    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = true) {
        if (shouldShowKeyboard) {
            focusRequester.requestFocus()
        }
        viewModel.eventFlow.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.uiText.asString(context)
                    )
                }
                else -> return@collectLatest
            }
        }

    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colors.surface),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                ) {
                    Spacer(modifier = Modifier.height(SpaceLarge))
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = ProfilePictureSizeMedium / 2f)
                                .clip(MaterialTheme.shapes.medium)
                                .background(MediumGray)
                        ) {
                            state.post?.let { post ->
                                AsyncImage(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .data(post.imageUrl).build(),
                                    contentDescription = "Post image",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(SpaceLarge)
                                ) {
                                    ActionRow(
                                        username = post.username ?: "",
                                        modifier = Modifier.fillMaxWidth(),
                                        onLikeClick = {
                                            viewModel.onEvent(PostDetailEvent.LikePost)
                                        },
                                        onCommentClick = {
                                            focusRequester.requestFocus()
                                        },
                                        onShareClick = {
                                            context.sendSharePostIntent(postId = post.id)
                                        },
                                        onUsernameClick = {
                                            onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}")
                                        },
                                        isLiked = post.isLiked
                                    )
                                    Spacer(modifier = Modifier.height(SpaceSmall))
                                    Text(
                                        text = post.description,
                                        fontSize = 16.sp,
                                        style = MaterialTheme.typography.body2,
                                    )
                                    Spacer(modifier = Modifier.height(SpaceMedium))
                                    Text(
                                        modifier = Modifier.clickable {
                                            onNavigate(Screen.PersonListScreen.route + "/${post.id}")
                                        },
                                        text = stringResource(
                                            id = R.string.x_likes, post.likeCount
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.body2
                                    )
                                }
                            }
                        }
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(state.post?.profilePictureProfile).build(),
                            contentDescription = stringResource(id = R.string.profile_picture),
                            modifier = Modifier
                                .size(ProfilePictureSizeMedium)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter),
                            contentScale = ContentScale.Crop
                        )

                        if (state.isLoadingPost) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.height(SpaceLarge))
            }
            items(state.comments) { comment ->
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = SpaceLarge, vertical = SpaceSmall
                        ), comment = comment,
                    onLikeClick = {
                        viewModel.onEvent(PostDetailEvent.LikeComment(comment.commentId))
                    },
                    onLikedByPeopleClick = {
                        onNavigate(Screen.PersonListScreen.route + "/${comment.commentId}")
                    }
                )
            }
        }

        SendTextField(
            state = viewModel.commentTextFieldState.value,
            onValueChange = {
                viewModel.onEvent(PostDetailEvent.EnteredComment(it))
            },
            onSend = {
                viewModel.onEvent(PostDetailEvent.Comment)
            },
            hint = stringResource(id = R.string.enter_a_comment),
            isLoading = viewModel.commentState.value.isLoading,
            focusRequester = focusRequester
        )

    }
}