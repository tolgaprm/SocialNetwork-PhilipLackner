package com.prmto.socialnetwork_philiplackner.core.domain.models

data class Comment(
    val commentId: String,
    val username: String,
    val profileImageUrl: String?,
    val formattedTime: String,
    val commentText: String,
    val likeCount: Int,
    val isLiked: Boolean,
)
