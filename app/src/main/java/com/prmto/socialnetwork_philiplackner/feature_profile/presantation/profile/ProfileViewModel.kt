package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.domain.usecase.GetOwnUserIdUseCase
import com.prmto.socialnetwork_philiplackner.core.presentation.PagingState
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.util.*
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.PostUseCases
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
    private val postLiker: PostLiker,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _toolbarState = mutableStateOf(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf(PagingState<Post>())
    val pagingState: State<PagingState<Post>> = _pagingState


    private val paginator = DefaultPaginator(onLoadUpdated = { isLoading ->
        _pagingState.value = pagingState.value.copy(isLoading = isLoading)
    }, onRequest = { page ->
        val userId = savedStateHandle.get<String>("userId") ?: getOwnUserId()
        profileUseCases.getPostsForProfile(userId = userId, page = page)
    }, onSuccess = { posts ->
        _pagingState.value = pagingState.value.copy(
            items = pagingState.value.items + posts, endReached = posts.isEmpty()
        )
    }, onError = { uiText ->
        _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
    })

    init {
        loadNextPosts()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LikedPost -> {
                toggleLikeForParent(parentId = event.postId)
            }

            is ProfileEvent.DismissLogoutDialog -> {
                _state.value = _state.value.copy(
                    isLogoutDialogVisible = false
                )
            }

            is ProfileEvent.ShowLogoutDialog -> {
                _state.value = _state.value.copy(
                    isLogoutDialogVisible = true
                )
            }

            is ProfileEvent.Logout -> {
                profileUseCases.logoutUseCase()
            }

            is ProfileEvent.DeletePost -> {
                deletePost(event.postId)
            }
        }
    }

    private fun deletePost(postId: String) {
        viewModelScope.launch {
            when (val result = postUseCases.deletePostUseCase(postId)) {
                is Resource.Success -> {
                    _pagingState.value = pagingState.value.copy(
                        items = pagingState.value.items.filter { it.id != postId }
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.successfuly_deleted_post)
                        )
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError()))
                }
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

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
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
                        isLoading = false, profile = result.data
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
}