package com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.model

data class WSClientMessage(
    val toId: String,
    val text: String,
    val chatId: String?,
)
