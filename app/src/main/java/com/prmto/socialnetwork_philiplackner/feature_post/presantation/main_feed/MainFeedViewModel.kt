package com.prmto.socialnetwork_philiplackner.feature_post.presantation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.presentation.PagingState
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.util.DefaultPaginator
import com.prmto.socialnetwork_philiplackner.core.util.Event
import com.prmto.socialnetwork_philiplackner.core.util.ParentType
import com.prmto.socialnetwork_philiplackner.core.util.PostLiker
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val postLiker: PostLiker
) : ViewModel() {

    private val _pagingState = mutableStateOf(PagingState<Post>())
    val pagingState: State<PagingState<Post>> = _pagingState

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator<Post>(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(isLoading = isLoading)
        },
        onRequest = { page ->
            postUseCases.getPostsFollowsUseCase(page)
        },
        onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty()
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
        }
    )

    init {
        loadNextPosts()
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun onEvent(event: MainFeedEvent) {
        when (event) {
            is MainFeedEvent.LikedPost -> {
                toggleLikeForParent(
                    parentId = event.postId
                )
            }

        }
    }

    private fun toggleLikeForParent(
        parentId: String
    ) {
        viewModelScope.launch {
            postLiker.toggleLike(posts = pagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.toggleLikeForParent(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked
                    )
                },
                onStateUpdated = { posts ->
                    _pagingState.value = pagingState.value.copy(
                        items = posts
                    )
                }
            )
        }
    }
}



