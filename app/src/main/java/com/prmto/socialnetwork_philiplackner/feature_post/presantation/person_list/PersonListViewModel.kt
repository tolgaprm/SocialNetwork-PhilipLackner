package com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.core.domain.usecase.GetOwnUserIdUseCase
import com.prmto.socialnetwork_philiplackner.core.domain.usecase.ToggleFollowStateForUserUseCase
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val toggleFollowStateForUserUseCase: ToggleFollowStateForUserUseCase,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PersonListState())
    val state: State<PersonListState> = _state

    private val _ownUserId = mutableStateOf("")
    val ownUserId: State<String> = _ownUserId

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("parentId")?.let { parentId ->
            getLikesForParent(parentId)
            _ownUserId.value = getOwnUserIdUseCase()
        }
    }

    private fun getLikesForParent(parentId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = postUseCases.getLikesForParentUseCase(parentId = parentId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        users = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError()))
                }
            }
        }
    }

    fun onEvent(event: PersonListEvent) {
        when (event) {
            is PersonListEvent.ToggleFollowState -> {
                toggleFollowStateForUser(
                    userId = event.userId,
                    isFollowing = event.isFollowing
                )
            }
        }
    }

    private fun toggleFollowStateForUser(userId: String, isFollowing: Boolean) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                users = state.value.users.map {
                    if (it.userId == userId) {
                        it.copy(isFollowing = !it.isFollowing)
                    } else it
                }
            )

            val result = toggleFollowStateForUserUseCase(
                userId = userId,
                isFollowing = isFollowing
            )

            when (result) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        users = state.value.users.map {
                            if (it.userId == userId) {
                                it.copy(isFollowing = isFollowing)
                            } else it
                        }
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }
}