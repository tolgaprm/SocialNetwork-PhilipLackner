package com.prmto.socialnetwork_philiplackner.core.domain.models

data class UserItem(
    val userId: String,
    val userName: String,
    val profilePictureUrl: String,
    val bio: String,
    val isFollowing: Boolean
)
