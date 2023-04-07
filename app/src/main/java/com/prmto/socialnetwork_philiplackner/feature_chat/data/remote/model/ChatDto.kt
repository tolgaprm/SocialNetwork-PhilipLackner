package com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.model

import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Chat

data class ChatDto(
    val chatId: String,
    val remoteUserId: String?,
    val remoteUsername: String?,
    val remoteUserProfileUrl: String?,
    val lastMessage: String?,
    val timestamp: Long?,
) {
    fun toChat(): Chat? {
        return Chat(
            chatId = chatId,
            remoteUserId = remoteUserId ?: return null,
            remoteUsername = remoteUsername ?: return null,
            remoteUserProfileUrl = remoteUserProfileUrl ?: return null,
            lastMessage = lastMessage ?: return null,
            timestamp = timestamp ?: return null,
        )
    }
}