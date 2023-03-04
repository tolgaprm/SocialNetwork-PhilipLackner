package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.profile

import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val isLogoutDialogVisible: Boolean = false
)
