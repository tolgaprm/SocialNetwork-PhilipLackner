package com.prmto.socialnetwork_philiplackner.feature_post.presantation.create_post

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.core.domain.states.StandardTextFieldState
import com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _descriptionState = mutableStateOf(StandardTextFieldState())
    val descriptionState: State<StandardTextFieldState> = _descriptionState

    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImageUri: State<Uri?> = _chosenImageUri


    fun onEvent(event: CreatePostEvent) {
        when (event) {
            is CreatePostEvent.EnteredDescription -> {
                _descriptionState.value = descriptionState.value.copy(
                    text = event.description
                )
            }
            is CreatePostEvent.PickImage -> {
                _chosenImageUri.value = event.uri
            }
            is CreatePostEvent.CropImage -> {
                _chosenImageUri.value = event.uri
            }
            is CreatePostEvent.PostImage -> {
                chosenImageUri.value?.let { uri ->
                    viewModelScope.launch {
                        postUseCases.createPostUseCase(
                            description = descriptionState.value.text,
                            imageUri = uri
                        )
                    }
                }
            }
        }
    }
}