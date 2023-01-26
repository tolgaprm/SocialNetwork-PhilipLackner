package com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.domain.models.UserItem
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.repository.ProfileRepository

class SearchUserUseCase(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(query: String): Resource<List<UserItem>> {
        if (query.isBlank()) {
            return Resource.Success(data = emptyList())
        }
        return profileRepository.searchUSer(query = query)
    }
}