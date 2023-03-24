package com.prmto.socialnetwork_philiplackner.feature_chat.domain.model

data class Chat(
    val remoteUsername: String,
    val remoteUserProfileUrl: String,
    val lastMessage: String,
    val lastMessageFormattedTime: String
)
