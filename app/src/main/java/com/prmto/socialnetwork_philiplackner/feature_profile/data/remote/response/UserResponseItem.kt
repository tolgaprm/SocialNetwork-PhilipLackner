package com.prmto.socialnetwork_philiplackner.feature_profile.data.remote.response

import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.UserItem

data class UserResponseItem(
    val userId: String,
    val userName: String,
    val profilePictureUrl: String,
    val bio: String,
    val isFollowing: Boolean
) {
    fun toUser(): UserItem {
        return UserItem(
            userId = userId,
            profilePictureUrl = profilePictureUrl,
            userName = userId,
            bio = bio,
            isFollowing = isFollowing
        )
    }
}
