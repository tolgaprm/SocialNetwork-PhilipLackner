package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile

sealed class ProfileEvent {
    data class LikedPost(val postId: String, val isLiked: Boolean):ProfileEvent()
}
