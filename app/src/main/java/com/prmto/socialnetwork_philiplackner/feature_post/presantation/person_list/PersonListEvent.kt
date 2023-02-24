package com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list

sealed class PersonListEvent {
    data class ToggleFollowState(val userId: String, val isFollowing: Boolean) : PersonListEvent()
}
