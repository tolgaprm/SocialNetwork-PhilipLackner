package com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.model

import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Message

data class MessageDto(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?,
    val id: String,
) {
    fun toMessage(): Message {
        return Message(
            fromId = fromId,
            toId = toId,
            text = text,
            formattedTime = timestamp.toString(),
            chatId = chatId
        )
    }
}