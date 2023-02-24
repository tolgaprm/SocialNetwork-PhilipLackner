package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.domain.usecase.GetOwnUserIdUseCase
import com.prmto.socialnetwork_philiplackner.core.presentation.PagingState
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.util.Event
import com.prmto.socialnetwork_philiplackner.core.util.ParentType
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.PostUseCases
import com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list.PostEvent
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val postUseCases: PostUseCases,
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _toolbarState = mutableStateOf(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var page = 0
    private val _pagingState = mutableStateOf(PagingState<Post>())
    val pagingState: State<PagingState<Post>> = _pagingState


    init {
        loadNextPosts()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LikedPost -> {
                toggleLikeForParent(
                    parentId = event.postId,
                    isLiked = event.isLiked
                )
            }
        }
    }


    fun loadNextPosts() {
        viewModelScope.launch {
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserId()
            _pagingState.value = pagingState.value.copy(isLoading = true)
            when (val result = profileUseCases.getPostsForProfile(userId = userId, page = page)) {
                is Resource.Success -> {
                    val posts = result.data ?: emptyList()
                    _pagingState.value = pagingState.value.copy(
                        items = pagingState.value.items + posts,
                        endReached = posts.isEmpty(),
                        isLoading = false
                    )
                    page++
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError())
                    )
                    _pagingState.value = pagingState.value.copy(isLoading = false)
                }
            }
        }
    }


    fun setExpandedRatio(ratio: Float) {
        _toolbarState.value = _toolbarState.value.copy(
            expandedRatio = ratio
        )
    }

    fun setToolbarOffsetY(value: Float) {
        _toolbarState.value = _toolbarState.value.copy(
            toolbarOffsetY = value
        )
    }


    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = profileUseCases.getProfile(
                userId ?: getOwnUserId()
            )
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        profile = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            result.uiText ?: UiText.unknownError()
                        )
                    )
                }
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