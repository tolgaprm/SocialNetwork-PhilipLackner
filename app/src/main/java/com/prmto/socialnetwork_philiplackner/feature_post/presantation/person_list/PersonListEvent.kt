package com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list

import com.prmto.socialnetwork_philiplackner.core.domain.models.Post

sealed class PersonListEvent {
    data class ToggleFollowState(val userId: String, val isFollowing: Boolean) : PersonListEvent()
}
