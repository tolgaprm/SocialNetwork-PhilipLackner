package com.prmto.socialnetwork_philiplackner.core.data.dto.response

import com.prmto.socialnetwork_philiplackner.core.domain.models.UserItem

data class UserItemDto(
    val userId: String,
    val userName: String,
    val profilePictureUrl: String,
    val bio: String,
    val isFollowing: Boolean
) {
    fun toUserItem(): UserItem {
        return UserItem(
            userId = userId,
            profilePictureUrl = profilePictureUrl,
            userName = userName,
            bio = bio,
            isFollowing = isFollowing
        )
    }
}
