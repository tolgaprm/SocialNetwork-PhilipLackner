package com.prmto.socialnetwork_philiplackner.feature_activity.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.socialnetwork_philiplackner.core.domain.models.Activity
import com.prmto.socialnetwork_philiplackner.feature_activity.data.remote.ActivityApi
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ActivitySource @Inject constructor(
    private val activityApi: ActivityApi
) : PagingSource<Int, Activity>() {
    override fun getRefreshKey(state: PagingState<Int, Activity>): Int? {
        return state.anchorPosition
    }

    private var currentPage = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Activity> {
        return try {
            val nextPage = params.key ?: currentPage

            val activities = activityApi.getActivities(page = nextPage)

            LoadResult.Page(
                data = activities.map { it.toActivity() },
                prevKey = if (nextPage == 0) null else nextPage.minus(1),
                nextKey = if (activities.isEmpty()) null else currentPage.plus(1)
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