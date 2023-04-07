package com.prmto.socialnetwork_philiplackner.feature_chat.domain.use_case

import com.prmto.socialnetwork_philiplackner.feature_chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

class ObserveChatEvents(
    private val repository: ChatRepository,
) {

    operator fun invoke(): Flow<WebSocket.Event> {
        return repository.observeChatEvents()
    }
}