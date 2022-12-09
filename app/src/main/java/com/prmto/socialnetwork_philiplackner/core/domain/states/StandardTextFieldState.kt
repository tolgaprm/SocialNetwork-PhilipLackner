package com.prmto.socialnetwork_philiplackner.core.domain.states

import com.prmto.socialnetwork_philiplackner.core.domain.util.Error

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)