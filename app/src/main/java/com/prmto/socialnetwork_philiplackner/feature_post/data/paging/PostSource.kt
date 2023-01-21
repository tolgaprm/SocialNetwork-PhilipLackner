package com.prmto.socialnetwork_philiplackner.feature_post.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.socialnetwork_philiplackner.core.data.remote.PostApi
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class PostSource @Inject constructor(
    private val postApi: PostApi,
    private val source: PostSource.Source
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    private var currentPage = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPage = params.key ?: currentPage

            val posts = when (source) {
                is Source.Follows -> postApi.getPostsForFollows(
                    page = nextPage,
                    pageSize = Constants.PAGE_SIZE_POST
                )
                is Source.Profile -> postApi.getPostsForProfile(
                    page = nextPage,
                    pageSize = Constants.PAGE_SIZE_POST,
                    userId = source.userId
                )

            }

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

    sealed class Source {
        object Follows : Source()
        data class Profile(val userId: String) : Source()
    }
}