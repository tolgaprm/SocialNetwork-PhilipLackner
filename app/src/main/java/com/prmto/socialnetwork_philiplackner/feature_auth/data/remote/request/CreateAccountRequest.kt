package com.prmto.socialnetwork_philiplackner.feature_auth.data.remote.request

data class CreateAccountRequest(
    val username: String,
    val email: String,
    val password: String
)
