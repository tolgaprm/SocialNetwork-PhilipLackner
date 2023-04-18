package com.prmto.socialnetwork_philiplackner.feature_chat.domain.use_case

import com.prmto.socialnetwork_philiplackner.feature_chat.domain.repository.ChatRepository

class InitializeRepository(
    private val repository: ChatRepository,
) {

    operator fun invoke() {
        repository.initiliaze()
    }
}