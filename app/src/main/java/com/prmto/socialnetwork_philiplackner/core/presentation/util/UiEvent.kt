package com.prmto.socialnetwork_philiplackner.core.presentation.util

import com.prmto.socialnetwork_philiplackner.core.util.UiText

sealed class UiEvent {
    data class ShowSnackbar(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
}
