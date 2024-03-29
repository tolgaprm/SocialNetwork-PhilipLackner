package com.prmto.socialnetwork_philiplackner.core.presentation.util

import com.prmto.socialnetwork_philiplackner.core.util.Event
import com.prmto.socialnetwork_philiplackner.core.util.UiText

sealed class UiEvent : Event() {
    data class ShowSnackbar(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
}
