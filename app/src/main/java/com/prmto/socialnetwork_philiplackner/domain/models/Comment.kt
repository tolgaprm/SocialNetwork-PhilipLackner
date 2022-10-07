package com.prmto.socialnetwork_philiplackner.domain.models

data class Comment(
    val commentId: Int = -1,
    val username: String = "",
    val profilePictureUrl: String,
    val timestamp: Long = System.currentTimeMillis(),
    val comment: String = "",
    val likeCount: Int = 12,
    val isLiked: Boolean = false,
)
