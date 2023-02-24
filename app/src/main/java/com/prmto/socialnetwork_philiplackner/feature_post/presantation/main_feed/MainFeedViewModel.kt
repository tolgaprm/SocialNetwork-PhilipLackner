package com.prmto.socialnetwork_philiplackner.feature_post.presantation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.prmto.socialnetwork_philiplackner.core.util.Event
import com.prmto.socialnetwork_philiplackner.core.util.ParentType
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.PostUseCases
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list.PostEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _mainFeedState = mutableStateOf(MainFeedState())
    val mainFeedState: State<MainFeedState> = _mainFeedState

    val posts = postUseCases.getPostsFollowsUseCase().cachedIn(viewModelScope)

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: MainFeedEvent) {
        when (event) {
            is MainFeedEvent.LoadMorePosts -> {
                _mainFeedState.value = _mainFeedState.value.copy(
                    isLoadingNewPosts = true
                )
            }
            is MainFeedEvent.LoadedPage -> {
                _mainFeedState.value = _mainFeedState.value.copy(
                    isLoadingFirstTime = false,
                    isLoadingNewPosts = false
                )
            }
            is MainFeedEvent.LikedPost -> {
                toggleLikeForParent(
                    parentId = event.postId,
                    isLiked = event.isLiked,
                    updateLikeState = {

                    },
                    defaultLikeStateWhenOnError = {

                    }
                )
            }
        }
    }

    private fun toggleLikeForParent(
        parentId: String,
        isLiked: Boolean,
        updateLikeState: () -> Unit = {},
        defaultLikeStateWhenOnError: () -> Unit = {}
    ) {
        viewModelScope.launch {
            updateLikeState()
            val result = postUseCases.toggleLikeForParent(
                parentId = parentId,
                parentType = ParentType.Post.type,
                isLiked = isLiked
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(PostEvent.LikedPost)
                }
                is Resource.Error -> {
                    defaultLikeStateWhenOnError()
                }
            }
        }
    }

}