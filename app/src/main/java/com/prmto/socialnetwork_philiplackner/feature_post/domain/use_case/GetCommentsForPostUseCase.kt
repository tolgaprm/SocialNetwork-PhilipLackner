package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.domain.models.Comment
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository

class GetCommentsForPostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String): Resource<List<Comment>> {
        return repository.getCommentsForPost(postId = postId)
    }
}