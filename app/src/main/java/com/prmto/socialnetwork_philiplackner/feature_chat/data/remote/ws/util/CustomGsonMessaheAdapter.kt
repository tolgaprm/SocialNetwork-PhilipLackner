package com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ws.util

import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ws.model.WsMessage
import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class CustomGsonMessageAdapter<T> private constructor(
    private val gson: Gson,
) : MessageAdapter<T> {

    override fun fromMessage(message: Message): T {
        val payload = when (message) {
            is Message.Text -> message.value
            is Message.Bytes -> ""
        }
        val delimiterIndex = payload.indexOf("#")
        if (delimiterIndex == -1) {
            return Any() as T
        }
        val type = payload.substring(0, delimiterIndex).toIntOrNull()
        if (type == null) {
            println("Invalid format")
            return Any() as T
        }
        val json = payload.substring(delimiterIndex + 1, payload.length)
        val clazz = when (type) {
            WebSocketObject.MESSAGE.ordinal -> WsMessage::class.java
            else -> Any::class.java
        }
        return gson.fromJson(json, clazz) as T
    }

    override fun toMessage(data: T): Message {
        val clazz = when (data) {
            is WsMessage -> WsMessage::class.java
            else -> Any::class.java
        }
        val type = when (data) {
            is WsMessage -> WebSocketObject.MESSAGE.ordinal
            else -> -1
        }
        val socketString = "$type#${gson.toJson(data, clazz)}"
        return Message.Text(socketString)
    }

    class Factory(
        private val gson: Gson = DEFAULT_GSON,
    ) : MessageAdapter.Factory {

        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> {
            return CustomGsonMessageAdapter<Any>(gson)
        }

        companion object {
            private val DEFAULT_GSON = Gson()
        }
    }
}
