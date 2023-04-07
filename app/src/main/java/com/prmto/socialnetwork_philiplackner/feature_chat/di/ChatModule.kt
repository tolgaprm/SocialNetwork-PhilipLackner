package com.prmto.socialnetwork_philiplackner.feature_chat.di

import android.app.Application
import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ChatApi
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ChatService
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.util.CustomGsonMessageAdapter
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.util.FlowStreamAdapter
import com.prmto.socialnetwork_philiplackner.feature_chat.data.repository.ChatRepositoryImpl
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.repository.ChatRepository
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.use_case.*
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {


    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideScarlet(app: Application, okHttpClient: OkHttpClient, gson: Gson): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory("ws://192.168.0.2:8001/api/chat/websocket"))
            .addMessageAdapterFactory(CustomGsonMessageAdapter.Factory(gson))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .lifecycle(AndroidLifecycle.ofApplicationForeground(app))
            .build()
    }

    @Provides
    @Singleton
    fun provideChatService(scarlet: Scarlet): ChatService {
        return scarlet.create()
    }

    @Provides
    @Singleton
    fun provideChatApi(
        client: OkHttpClient,
    ): ChatApi {
        return Retrofit.Builder()
            .baseUrl(ChatApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        chatService: ChatService,
        chatApi: ChatApi,
    ): ChatRepository {
        return ChatRepositoryImpl(
            chatApi = chatApi,
            chatService = chatService
        )
    }

    @Provides
    @Singleton
    fun provideChatUseCases(
        chatRepository: ChatRepository,
    ): ChatUseCases {
        return ChatUseCases(
            sendMessage = SendMessage(chatRepository),
            observeChatEvents = ObserveChatEvents(chatRepository),
            observeMessages = ObserveMessages(chatRepository),
            getChatForUser = GetChatForUser(chatRepository)
        )
    }
}