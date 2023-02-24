package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.domain.models.UserItem
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository

class GetLikesForParentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        parentId: String
    ): Resource<List<UserItem>> {
        return repository.getLikesForParent(parentId)
    }
}