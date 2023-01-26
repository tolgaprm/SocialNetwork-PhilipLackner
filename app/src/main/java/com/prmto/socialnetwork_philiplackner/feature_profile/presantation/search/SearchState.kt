package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.search

import com.prmto.socialnetwork_philiplackner.core.domain.models.UserItem

data class SearchState(
    val userItems: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
