package com.prmto.socialnetwork_philiplackner.domain.models

import com.prmto.socialnetwork_philiplackner.domain.util.ActivityAction

data class Activity(
    val username: String,
    val actionType: ActivityAction,
    val formattedTime: String,
)
