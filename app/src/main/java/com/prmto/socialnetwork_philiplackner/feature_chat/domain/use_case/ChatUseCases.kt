package com.prmto.socialnetwork_philiplackner.feature_chat.domain.use_case

data class ChatUseCases(
    val sendMessage: SendMessage,
    val observeChatEvents: ObserveChatEvents,
    val observeMessages: ObserveMessages,
    val getChatForUser: GetChatForUser,
)
