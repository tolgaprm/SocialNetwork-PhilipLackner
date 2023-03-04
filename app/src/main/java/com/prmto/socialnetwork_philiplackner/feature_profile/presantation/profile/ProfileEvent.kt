package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile

sealed class ProfileEvent {
    data class LikedPost(val postId: String) : ProfileEvent()

    object DismissLogoutDialog : ProfileEvent()
    object ShowLogoutDialog : ProfileEvent()

    object Logout : ProfileEvent()
}
