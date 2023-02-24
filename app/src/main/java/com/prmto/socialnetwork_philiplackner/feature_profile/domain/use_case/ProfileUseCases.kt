package com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.domain.usecase.ToggleFollowStateForUserUseCase

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val getSkills: GetSkillsUseCase,
    val updateProfile: UpdateProfileUseCase,
    val setSkillsSelected: SetSkillsSelectedUseCase,
    val getPostsForProfile: GetPostsForProfileUseCase,
    val searchUser: SearchUserUseCase,
    val toggleFollowStateForUser: ToggleFollowStateForUserUseCase
)
