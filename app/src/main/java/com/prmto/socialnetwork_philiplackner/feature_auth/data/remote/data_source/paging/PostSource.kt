package com.prmto.socialnetwork_philiplackner.feature_auth.data.remote.data_source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.feature_post.data.data_source.remote.PostApi
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class PostSource @Inject constructor(
    private val postApi: PostApi
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    private var currentPage = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPage = params.key ?: currentPage
            val posts = postApi.getPostsForFollows(
                page = nextPage,
                pageSize = Constants.PAGE_SIZE_POST
            )

            LoadResult.Page(
                data = posts,
                prevKey = if (nextPage == 0) null else nextPage.minus(1),
                nextKey = if (posts.isEmpty()) null else currentPage.plus(1)
            ).also {
                currentPage++
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}