package com.prmto.socialnetwork_philiplackner.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(

) : ViewModel() {

    private val _toolbarState = mutableStateOf(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState

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

}