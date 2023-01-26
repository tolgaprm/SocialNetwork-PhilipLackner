package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.search

sealed class SearchEvent {
    data class Query(val query: String) : SearchEvent()
    data class ToggleFollowState(val userId: String, val isFollowing: Boolean) : SearchEvent()
}
