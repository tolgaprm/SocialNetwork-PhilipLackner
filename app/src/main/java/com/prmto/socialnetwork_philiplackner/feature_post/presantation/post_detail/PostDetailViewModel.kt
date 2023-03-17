package com.prmto.socialnetwork_philiplackner.feature_post.presantation.post_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.states.StandardTextFieldState
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.util.ParentType
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
                val post = state.value.post
                val isLiked = post?.isLiked == true
                val currentlyLikedCount = post?.likeCount ?: 0
                toggleLikeForParent(
                    parentId = state.value.post?.id ?: return,
                    parentType = ParentType.Post.type,
                    isLiked = isLiked,
                    updateLikeState = {
                        _state.value = _state.value.copy(
                            post = post?.copy(
                                isLiked = !isLiked,
                                likeCount = if (isLiked) {
                                    post.likeCount - 1
                                } else post.likeCount + 1

                            )
                        )
                    },
                    defaultLikeStateWhenOnError = {
                        _state.value = _state.value.copy(
                            post = post?.copy(
                                isLiked = isLiked,
                                likeCount = currentlyLikedCount
                            )
                        )
                    }
                )
            }
            is PostDetailEvent.Comment -> {
                if (commentTextFieldState.value.text.isBlank()) {
                    _commentTextFieldState.value = commentTextFieldState.value.copy(
                        error = CommentError.FieldEmpty
                    )
                } else {
                    createComment(
                        postId = savedStateHandle.get<String>("postId") ?: "",
                        comment = commentTextFieldState.value.text,
                    )
                }
            }
            is PostDetailEvent.LikeComment -> {
                val comments = state.value.comments
                val comment = comments.find { it.commentId == event.commentId }
                val isLiked = comment?.isLiked == true
                val currentlyLikedCount = comment?.likeCount ?: 0
                toggleLikeForParent(
                    parentId = event.commentId,
                    parentType = ParentType.Comment.type,
                    isLiked = isLiked,
                    updateLikeState = {
                        _state.value = _state.value.copy(
                            comments = comments.map { comment ->
                                if (comment.commentId == event.commentId) {
                                    comment.copy(
                                        isLiked = !isLiked,
                                        likeCount = if (isLiked) {
                                            comment.likeCount - 1
                                        } else comment.likeCount + 1
                                    )
                                } else comment
                            }
                        )
                    },
                    defaultLikeStateWhenOnError = {
                        _state.value = _state.value.copy(
                            comments = comments.map {
                                if (it.commentId == event.commentId) {
                                    it.copy(
                                        isLiked = isLiked,
                                        likeCount = currentlyLikedCount
                                    )
                                } else it
                            }
                        )
                    }
                )
            }
            is PostDetailEvent.EnteredComment -> {
                _commentTextFieldState.value = StandardTextFieldState(text = event.comment)
            }
        }
    }

    private fun toggleLikeForParent(
        parentId: String,
        parentType: Int,
        isLiked: Boolean,
        updateLikeState: () -> Unit = {},
        defaultLikeStateWhenOnError: () -> Unit = {}
    ) {
        viewModelScope.launch {
            updateLikeState()
            val result = postUseCases.toggleLikeForParent(
                parentId = parentId,
                parentType = parentType,
                isLiked = isLiked
            )
            when (result) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    defaultLikeStateWhenOnError()
                }
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