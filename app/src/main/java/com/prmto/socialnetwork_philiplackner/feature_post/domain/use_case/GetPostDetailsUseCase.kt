package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository

class GetPostDetailsUseCase(private val repository: PostRepository) {

    suspend operator fun invoke(postId: String): Resource<Post> {
        return repository.getPostDetails(postId = postId)
    }
}