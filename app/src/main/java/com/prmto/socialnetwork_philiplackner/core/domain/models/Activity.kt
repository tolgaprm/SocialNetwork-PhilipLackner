package com.prmto.socialnetwork_philiplackner.core.domain.models


import com.prmto.socialnetwork_philiplackner.feature_activity.domain.ActivityType

data class Activity(
    val userId: String,
    val parentId: String,
    val username: String,
    val activityType: ActivityType,
    val formattedTime: String,
)
