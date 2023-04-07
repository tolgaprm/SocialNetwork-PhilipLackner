package com.prmto.socialnetwork_philiplackner.feature_chat.data.remote

import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.model.ChatDto
import retrofit2.http.GET

interface ChatApi {

    @GET("/api/chats")
    suspend fun getChatsForUser(): List<ChatDto>

    companion object {
        val BASE_URL = "http://10.0.2.2:8001"
    }
}