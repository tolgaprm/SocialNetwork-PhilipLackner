package com.prmto.socialnetwork_philiplackner.feature_chat.data.repository

import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ChatApi
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.ChatService
import com.prmto.socialnetwork_philiplackner.feature_chat.data.remote.model.WSClientMessage
import com.prmto.socialnetwork_philiplackner.feature_chat.di.ScarletInstance
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Chat
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Message
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.OkHttpClient
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val okHttpClient: OkHttpClient
) : ChatRepository {

    private var chatService: ChatService? = null

    override fun initiliaze() {
        chatService = ScarletInstance.getNewInstance(okHttpClient)
    }

    override suspend fun getChatsForUser(): Resource<List<Chat>> {
        return try {
            val chats = chatApi.getChatsForUser().mapNotNull { it.toChat() }
            Resource.Success(data = chats)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }

    override suspend fun getMessagesForChat(
        chatId: String,
        page: Int,
        pageSize: Int,
    ): Resource<List<Message>> {
        return try {
            val messages = chatApi.getMessagesForChat(
                page = page,
                pageSize = pageSize,
                chatId = chatId
            ).map { it.toMessage() }
            Resource.Success(data = messages)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }


    override fun observeChatEvents(): Flow<WebSocket.Event> {
        return chatService
            ?.observeEvents()
            ?.receiveAsFlow()
            ?: flow { }
    }

    override fun observeMessages(): Flow<Message> {
        return chatService
            ?.observeMessages()
            ?.receiveAsFlow()
            ?.map { it.toMessage() }
            ?: flow { }
    }

    override fun sendMessage(toId: String, text: String, chatId: String?) {
        chatService?.sendMessage(
            message = WSClientMessage(
                toId = toId,
                text = text,
                chatId = chatId
            )
        )
    }


}