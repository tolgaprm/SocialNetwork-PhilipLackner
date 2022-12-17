package com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Profile
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.repository.ProfileRepository

class GetProfileUseCase(private val repository: ProfileRepository) {

    suspend operator fun invoke(userId: String): Resource<Profile> {
        return repository.getProfile(userId)
    }
}