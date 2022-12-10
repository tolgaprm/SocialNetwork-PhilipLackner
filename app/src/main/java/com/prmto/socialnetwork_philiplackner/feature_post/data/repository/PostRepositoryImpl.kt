package com.prmto.socialnetwork_philiplackner.feature_post.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.feature_auth.data.remote.data_source.paging.PostSource
import com.prmto.socialnetwork_philiplackner.feature_post.data.data_source.remote.PostApi
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi
) : PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE_POST
            ),
            pagingSourceFactory = {
                PostSource(postApi)
            }
        ).flow

}