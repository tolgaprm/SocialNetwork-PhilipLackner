package com.prmto.socialnetwork_philiplackner.feature_chat.domain.repository

import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Chat
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Message
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatsForUser(): Resource<List<Chat>>

    fun observeChatEvents(): Flow<WebSocket.Event>

    fun observeMessages(): Flow<Message>

    fun sendMessage(toId: String, text: String, chatId: String?)
}