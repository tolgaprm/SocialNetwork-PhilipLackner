package com.prmto.socialnetwork_philiplackner.feature_auth.presantation.login

sealed class LoginEvent {
    data class EnteredEmail(val email: String) : LoginEvent()
    data class EnteredPassword(val password: String) : LoginEvent()
    object Login : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
}
