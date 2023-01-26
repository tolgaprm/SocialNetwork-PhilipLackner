package com.prmto.socialnetwork_philiplackner.feature_activity.data.remote.dto

import com.prmto.socialnetwork_philiplackner.core.domain.models.Activity
import com.prmto.socialnetwork_philiplackner.core.util.DateFormatUtil
import com.prmto.socialnetwork_philiplackner.feature_activity.domain.ActivityType

data class ActivityDto(
    val id: String,
    val timestamp: Long,
    val userId: String,
    val parentId: String,
    val type: Int,
    val username: String
) {
    fun toActivity(): Activity {
        return Activity(
            userId = userId,
            parentId = parentId,
            username = username,
            activityType = when (type) {
                ActivityType.FollowedUser.type -> ActivityType.FollowedUser
                ActivityType.CommentedOnPost.type -> ActivityType.CommentedOnPost
                ActivityType.LikedComment.type -> ActivityType.LikedComment
                ActivityType.LikedPost.type -> ActivityType.LikedPost
                else -> ActivityType.FollowedUser
            },

            formattedTime = DateFormatUtil.timestampsToFormattedString(
                timestamp = System.currentTimeMillis(),
                pattern = "MMM dd, HH:mm"
            )
        )
    }
}
