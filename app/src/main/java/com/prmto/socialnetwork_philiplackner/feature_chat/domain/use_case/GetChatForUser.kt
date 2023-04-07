package com.prmto.socialnetwork_philiplackner.feature_chat.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Chat
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.repository.ChatRepository

class GetChatForUser(
    private val repository: ChatRepository,
) {

    suspend operator fun invoke(): Resource<List<Chat>> {
        return repository.getChatsForUser()
    }
}