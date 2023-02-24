package com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list

import com.prmto.socialnetwork_philiplackner.core.domain.models.UserItem

data class PersonListState(
    val users: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
