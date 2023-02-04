package com.prmto.socialnetwork_philiplackner.feature_post.presantation.main_feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.util.Screen
import kotlinx.coroutines.launch

@Composable
fun MainFeedScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: MainFeedViewModel = hiltViewModel()
) {

    val posts = viewModel.posts.collectAsLazyPagingItems()
    val state = viewModel.mainFeedState.value
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth()
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
            showBackArrow = false,
            navActions = {
                IconButton(onClick = {
                    onNavigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoadingFirstTime) {
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
            LazyColumn {
                items(posts) { post ->
                    com.prmto.socialnetwork_philiplackner.core.presentation.components.Post(
                        post = Post(
                            id = post?.id ?: "",
                            username = post?.username ?: "",
                            imageUrl = post?.imageUrl ?: "",
                            description = post?.description ?: "",
                            profilePictureProfile = post?.profilePictureProfile ?: "",
                            likeCount = post?.likeCount ?: 0,
                            commentCount = post?.commentCount ?: 0,
                            userId = post?.userId ?: "",
                            isLiked = post?.isLiked ?: false
                        ),
                        onPostClick = {
                            onNavigate(Screen.PostDetailScreen.route + "/${post?.id}")
                        },

                    )
                }

                item {
                    if (state.isLoadingNewPosts) {
                        CircularProgressIndicator(modifier = Modifier.align(BottomCenter))
                    }
                }

                posts.apply {
                    when {
                        loadState.refresh !is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadedPage)
                        }
                        loadState.append is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadMorePosts)
                        }
                        loadState.append is LoadState.NotLoading -> {
                            viewModel.onEvent(MainFeedEvent.LoadedPage)
                        }
                        loadState.append is LoadState.Error -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Error"
                                )
                            }
                        }
                    }
                }
            }
        }

    }


}