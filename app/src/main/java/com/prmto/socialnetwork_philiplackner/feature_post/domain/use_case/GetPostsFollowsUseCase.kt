package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository

class GetPostsFollowsUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        page: Int,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): Resource<List<Post>> {
        return repository.getPostForFollows(
            page = page,
            pageSize = page
        )
    }
}