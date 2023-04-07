package com.prmto.socialnetwork_philiplackner.feature_chat.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Message
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.repository.ChatRepository

class GetMessagesForChat(
    private val repository: ChatRepository,
) {

    suspend operator fun invoke(
        chatId: String,
        page: Int,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
    ): Resource<List<Message>> {
        return repository.getMessagesForChat(
            chatId = chatId,
            page = page,
            pageSize = pageSize
        )
    }
}