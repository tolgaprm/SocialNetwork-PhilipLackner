package com.prmto.socialnetwork_philiplackner.feature_chat.data.remote

import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.model.ChatDto
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.model.MessageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatApi {

    @GET("/api/chats")
    suspend fun getChatsForUser(): List<ChatDto>

    @GET("/api/chat/messages")
    suspend fun getMessagesForChat(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("chatId") chatId: String,
    ): List<MessageDto>

    companion object {
        val BASE_URL = "http://10.0.2.2:8001"
    }
}