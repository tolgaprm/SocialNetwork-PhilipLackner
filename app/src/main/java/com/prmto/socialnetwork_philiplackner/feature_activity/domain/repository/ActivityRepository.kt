package com.prmto.socialnetwork_philiplackner.feature_activity.domain.repository

import androidx.paging.PagingData
import com.prmto.socialnetwork_philiplackner.core.domain.models.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    val activities: Flow<PagingData<Activity>>
}