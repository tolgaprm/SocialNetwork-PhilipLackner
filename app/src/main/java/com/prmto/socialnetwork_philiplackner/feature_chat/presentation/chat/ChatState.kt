package com.prmto.socialnetwork_philiplackner.feature_chat.presentation.chat

import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Chat

data class ChatState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false,
)
