package com.prmto.socialnetwork_philiplackner.core.domain.models

import com.prmto.socialnetwork_philiplackner.feature_activity.domain.ActivityAction

data class Activity(
    val username: String,
    val actionType: ActivityAction,
    val formattedTime: String,
)
