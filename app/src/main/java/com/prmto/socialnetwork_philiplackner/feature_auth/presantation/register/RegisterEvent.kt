package com.prmto.socialnetwork_philiplackner.feature_auth.presantation.register

sealed class RegisterEvent {
    data class EnteredUsername(val value: String) : RegisterEvent()
    data class EnteredEmail(val value: String) : RegisterEvent()
    data class EnteredPassword(val value: String) : RegisterEvent()
    object TogglePasswordVisibility : RegisterEvent()
    object Register : RegisterEvent()
}
