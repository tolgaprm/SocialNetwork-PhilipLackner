package com.prmto.socialnetwork_philiplackner.feature_post.presantation.post_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.states.StandardTextFieldState
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.PostUseCases
import com.prmto.socialnetwork_philiplackner.feature_post.util.CommentError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _commentTextFieldState =
        mutableStateOf(StandardTextFieldState(error = CommentError.FieldEmpty))
    val commentTextFieldState: State<StandardTextFieldState> = _commentTextFieldState

    private val _commentState = mutableStateOf(CommentState())
    val commentState: State<CommentState> = _commentState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            loadPostDetails(postId = postId)
            loadCommentForPost(postId = postId)
        }
    }

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            is PostDetailEvent.LikePost -> {

            }
            is PostDetailEvent.Comment -> {
                if (commentTextFieldState.value.text.isBlank()) {
                    _commentTextFieldState.value = commentTextFieldState.value.copy(
                        error = CommentError.FieldEmpty
                    )
                } else {
                    createComment(
                        postId = savedStateHandle.get<String>("postId") ?: "",
                        comment = commentTextFieldState.value.text
                    )
                }
            }
            is PostDetailEvent.LikeComment -> {

            }
            is PostDetailEvent.SharePost -> {

            }
            is PostDetailEvent.EnteredComment -> {
                _commentTextFieldState.value = StandardTextFieldState(text = event.comment)
            }
        }
    }

    private fun createComment(postId: String, comment: String) {
        viewModelScope.launch {
            _commentState.value = CommentState(isLoading = true)
            val result = postUseCases.createComment(
                postId = postId,
                comment = comment
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(
                                R.string.comment_posted
                            )
                        )
                    )
                    _commentState.value = CommentState()
                    _commentTextFieldState.value = commentTextFieldState.value.copy(
                        text = "",
                        error = CommentError.FieldEmpty
                    )
                    loadCommentForPost(postId = postId)
                }
                is Resource.Error -> {
                    _commentState.value = CommentState()
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    private fun loadPostDetails(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoadingPost = true)
            when (val result = postUseCases.getPostDetails(postId = postId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoadingPost = false, post = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingPost = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError())
                    )
                }
            }
        }
    }

    private fun loadCommentForPost(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoadingComments = true)
            when (val result = postUseCases.getCommentsForPost(postId = postId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoadingComments = false, comments = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingComments = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError())
                    )
                }
            }
        }
    }

}