package com.prmto.socialnetwork_philiplackner.feature_post.data.remote.dto

import com.prmto.socialnetwork_philiplackner.core.domain.models.Comment
import com.prmto.socialnetwork_philiplackner.core.util.DateFormatUtil

data class CommentDto(
    val id: String,
    val username: String,
    val profileImageUrl: String?,
    val timestamp: Long,
    val commentText: String,
    val likeCount: Int,
    val isLiked: Boolean,
) {
    fun toComment(): Comment {
        return Comment(
            commentId = id,
            username = username,
            profileImageUrl = profileImageUrl,
            formattedTime =DateFormatUtil.timestampsToFormattedString(timestamp = timestamp, pattern = "MMM dd, HH:mm"),
            commentText = commentText,
            likeCount = likeCount,
            isLiked = isLiked
        )
    }
}
