package com.prmto.socialnetwork_philiplackner.feature_profile.domain.model

import com.prmto.socialnetwork_philiplackner.core.domain.models.User

data class Profile(
    val userId: String,
    val username: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val bannerUrl: String,
    val profilePictureUrl: String,
    val topSkills: List<Skill>,
    val gitHubUrl: String?,
    val instagramUrl: String?,
    val linkedInUrl: String?,
    val isOwnProfile: Boolean,
    val isFollowing: Boolean
) {
    fun toUser(): User {
        return User(
            userId = userId,
            profilePictureUrl = profilePictureUrl,
            username = username,
            bio = bio,
            followerCount = followerCount,
            followingCount = followingCount,
            postCount = postCount
        )
    }
}
