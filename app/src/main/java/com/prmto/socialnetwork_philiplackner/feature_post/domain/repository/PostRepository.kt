package com.prmto.socialnetwork_philiplackner.feature_post.domain.repository

import androidx.paging.PagingData
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    val posts: Flow<PagingData<Post>>
}