package com.prmto.socialnetwork_philiplackner.core.util

import com.prmto.socialnetwork_philiplackner.core.domain.models.Post

interface PostLiker {

    suspend fun toggleLike(
        posts: List<Post>,
        parentId: String,
        onRequest: suspend (Boolean) -> SimpleResource,
        onStateUpdated: (List<Post>) -> Unit
    )
}