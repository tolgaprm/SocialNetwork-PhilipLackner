package com.prmto.socialnetwork_philiplackner.feature_activity.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.socialnetwork_philiplackner.core.domain.models.Activity
import com.prmto.socialnetwork_philiplackner.core.util.Constants.DEFAULT_PAGE_SIZE
import com.prmto.socialnetwork_philiplackner.feature_activity.data.paging.ActivitySource
import com.prmto.socialnetwork_philiplackner.feature_activity.data.remote.ActivityApi
import com.prmto.socialnetwork_philiplackner.feature_activity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val activityApi: ActivityApi
) : ActivityRepository {

    override val activities: Flow<PagingData<Activity>>
        get() = Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                ActivitySource(activityApi = activityApi)
            }
        ).flow
}