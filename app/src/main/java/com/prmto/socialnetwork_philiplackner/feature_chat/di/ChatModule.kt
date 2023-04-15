package com.prmto.socialnetwork_philiplackner.feature_chat.di

import android.app.Application
import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ChatApi
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ChatService
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.util.CustomGsonMessageAdapter
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.util.FlowStreamAdapter
import com.prmto.socialnetwork_philiplackner.feature_chat.data.repository.ChatRepositoryImpl
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.repository.ChatRepository
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.use_case.*
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
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


    @Provides
    fun provideScarlet(application: Application,okHttpClient: OkHttpClient, gson: Gson): Scarlet {
        return Scarlet.Builder()
            .addMessageAdapterFactory(CustomGsonMessageAdapter.Factory(gson))
            .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
            .webSocketFactory(okHttpClient.newWebSocketFactory("ws://192.168.1.20:8001/api/chat/websocket"))
            .backoffStrategy(LinearBackoffStrategy(Constants.RECONNECT_INTERVAL))
            .build()
    }

    @Provides
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
    fun provideChatUseCases(
        chatRepository: ChatRepository,
    ): ChatUseCases {
        return ChatUseCases(
            sendMessage = SendMessage(chatRepository),
            observeChatEvents = ObserveChatEvents(chatRepository),
            observeMessages = ObserveMessages(chatRepository),
            getChatForUser = GetChatForUser(chatRepository),
            getMessagesForChat = GetMessagesForChat(chatRepository)
        )
    }
}