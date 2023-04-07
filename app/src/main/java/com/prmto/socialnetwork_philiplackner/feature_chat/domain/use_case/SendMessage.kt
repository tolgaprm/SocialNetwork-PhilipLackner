package com.prmto.socialnetwork_philiplackner.feature_chat.domain.use_case

import com.prmto.socialnetwork_philiplackner.feature_chat.domain.repository.ChatRepository

class SendMessage(
    private val repository: ChatRepository,
) {

    operator fun invoke(toId: String, text: String, chatId: String?) {
        if (text.isBlank()) {
            return
        }
        repository.sendMessage(
            toId = toId,
            text = text.trim(),
            chatId = chatId
        )
    }
}