package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository

class ToggleLikeForParentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        parentId: String,
        parentType: Int,
        isLiked: Boolean
    ): SimpleResource {
        return if (isLiked) {
            repository.unLikeParent(parentId = parentId, parentType = parentType)
        } else {
            repository.likeParent(parentId = parentId, parentType = parentType)
        }
    }
}