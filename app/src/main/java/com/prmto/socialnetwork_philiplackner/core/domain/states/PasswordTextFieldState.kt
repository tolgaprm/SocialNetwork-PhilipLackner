package com.prmto.socialnetwork_philiplackner.core.domain.states

import com.prmto.socialnetwork_philiplackner.core.domain.util.Error

data class PasswordTextFieldState(
    val text: String = "",
    val error: Error? = null,
    val isPasswordVisible: Boolean = false
)