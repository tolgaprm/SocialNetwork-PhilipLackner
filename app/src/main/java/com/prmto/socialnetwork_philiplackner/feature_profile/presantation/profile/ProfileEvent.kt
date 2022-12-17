package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile

sealed class ProfileEvent {
    data class GetProfile(val userId: String) : ProfileEvent()
}
