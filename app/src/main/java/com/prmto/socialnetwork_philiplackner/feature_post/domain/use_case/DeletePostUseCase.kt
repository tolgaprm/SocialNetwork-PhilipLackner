package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository

class DeletePostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        postId: String
    ): SimpleResource {
        return repository.deletePost(postId)
    }
}