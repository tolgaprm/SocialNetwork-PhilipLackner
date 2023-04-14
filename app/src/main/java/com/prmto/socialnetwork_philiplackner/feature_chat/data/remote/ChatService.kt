package com.prmto.socialnetwork_philiplackner.feature_chat.data.remote

import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.model.WSClientMessage
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.model.WsServerMessage
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel

interface ChatService {

    @Receive
    fun observeEvents(): ReceiveChannel<WebSocket.Event>

    @Send
    fun sendMessage(message: WSClientMessage)

    @Receive
    fun observeMessages(): ReceiveChannel<WsServerMessage>
}