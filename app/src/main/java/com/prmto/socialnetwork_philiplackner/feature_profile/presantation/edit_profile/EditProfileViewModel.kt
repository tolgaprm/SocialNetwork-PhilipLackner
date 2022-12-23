package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.edit_profile

import android.net.Uri
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
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.UpdateProfileData
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case.ProfileUseCases
import com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _userNameState = mutableStateOf(StandardTextFieldState())
    val userNameState: State<StandardTextFieldState> = _userNameState

    private val _githubTextFieldState = mutableStateOf(StandardTextFieldState())
    val githubTextFieldState: State<StandardTextFieldState> = _githubTextFieldState

    private val _instagramTextFieldState = mutableStateOf(StandardTextFieldState())
    val instagramTextFieldState: State<StandardTextFieldState> = _instagramTextFieldState

    private val _linkedinTextFieldState = mutableStateOf(StandardTextFieldState())
    val linkedinTextFieldState: State<StandardTextFieldState> = _linkedinTextFieldState

    private val _bioState = mutableStateOf(StandardTextFieldState())
    val bioState: State<StandardTextFieldState> = _bioState

    private val _skills = mutableStateOf(SkillsState())
    val skills: State<SkillsState> = _skills

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _bannerUri = mutableStateOf<Uri?>(null)
    val bannerUri: State<Uri?> = _bannerUri

    private val _profilePictureUri = mutableStateOf<Uri?>(null)
    val profilePictureUri: State<Uri?> = _profilePictureUri


    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getSkills()
            getProfile(userId = userId)
        }
    }

    private fun getSkills() {
        viewModelScope.launch {
            when (val result = profileUseCases.getSkillsUseCase()) {
                is Resource.Success -> {
                    _skills.value = _skills.value.copy(
                        skills = result.data ?: kotlin.run {
                            _eventFlow.emit(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_couldnt_load_skills)))
                            return@launch
                        }
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError()))
                    return@launch
                }
            }
        }
    }

    private fun updateProfile() {
        viewModelScope.launch {
            val result = profileUseCases.updateProfileUseCase(
                updateProfileData = UpdateProfileData(
                    username = userNameState.value.text,
                    bio = bioState.value.text,
                    gitHubUrl = githubTextFieldState.value.text,
                    instagramUrl = instagramTextFieldState.value.text,
                    linkedInUrl = linkedinTextFieldState.value.text,
                    skills = skills.value.selectedSkills,
                ),
                profilePictureUri = profilePictureUri.value,
                bannerImageUri = bannerUri.value
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.updated_profile)
                        )
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError()))
                }
            }
        }
    }

    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _profileState.value = _profileState.value.copy(isLoading = true)
            when (val result = profileUseCases.getProfileUseCase(userId = userId)) {
                is Resource.Success -> {
                    val profile = result.data ?: kotlin.run {
                        _eventFlow.emit(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_couldnt_load_profile)))
                        return@launch
                    }
                    _userNameState.value = _userNameState.value.copy(
                        text = profile.username
                    )
                    _githubTextFieldState.value = _githubTextFieldState.value.copy(
                        text = profile.gitHubUrl ?: ""
                    )
                    _instagramTextFieldState.value = _instagramTextFieldState.value.copy(
                        text = profile.instagramUrl ?: ""
                    )
                    _linkedinTextFieldState.value = _linkedinTextFieldState.value.copy(
                        text = profile.linkedInUrl ?: ""
                    )
                    _bioState.value = _bioState.value.copy(
                        text = profile.bio
                    )
                    _skills.value = _skills.value.copy(
                        selectedSkills = profile.topSkills
                    )
                    _profileState.value = _profileState.value.copy(
                        profile = profile,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError()))
                }
            }
        }
    }

    fun onEvent(event: EditProfileEvents) {
        when (event) {
            is EditProfileEvents.EnteredUserName -> {
                _userNameState.value = _userNameState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvents.EnteredGitHubUrl -> {
                _githubTextFieldState.value = _githubTextFieldState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvents.EnteredInstagramUrl -> {
                _instagramTextFieldState.value = _instagramTextFieldState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvents.EnteredLinkedInUrl -> {
                _linkedinTextFieldState.value = _linkedinTextFieldState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvents.EnteredBio -> {
                _bioState.value = _bioState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvents.CropProfileImage -> {
                _profilePictureUri.value = event.uri
            }

            is EditProfileEvents.CropBannerImage -> {
                _bannerUri.value = event.uri
            }

            is EditProfileEvents.SetSkillSelected -> {

            }

            is EditProfileEvents.UpdateProfile -> {
                updateProfile()
            }

        }
    }
}