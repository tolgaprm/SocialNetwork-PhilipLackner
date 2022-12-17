package com.prmto.socialnetwork_philiplackner.feature_profile.data.remote.response

import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Profile

data class ProfileResponse(
    val userId: String,
    val username: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val bannerUrl: String,
    val profilePictureUrl: String,
    val topSkillsUrls: List<String>,
    val gitHubUrl: String?,
    val instagramUrl: String?,
    val linkedInUrl: String?,
    val isOwnProfile: Boolean,
    val isFollowing: Boolean
) {
    fun toProfile(): Profile {
        return Profile(
            userId=userId,
            username = username,
            bio = bio,
            followerCount = followerCount,
            followingCount = followingCount,
            postCount = postCount,
            bannerUrl = bannerUrl,
            profilePictureUrl = profilePictureUrl,
            topSkillsUrls = topSkillsUrls,
            gitHubUrl = gitHubUrl,
            instagramUrl = instagramUrl,
            linkedInUrl = linkedInUrl,
            isOwnProfile = isOwnProfile,
            isFollowing = isFollowing
        )
    }
}
