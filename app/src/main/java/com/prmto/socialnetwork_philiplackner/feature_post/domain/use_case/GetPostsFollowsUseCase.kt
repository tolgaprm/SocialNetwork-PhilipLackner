package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

import androidx.paging.PagingData
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsFollowsUseCase(
    private val repository: PostRepository
) {

    operator fun invoke(): Flow<PagingData<Post>> {
        return repository.posts
    }
}