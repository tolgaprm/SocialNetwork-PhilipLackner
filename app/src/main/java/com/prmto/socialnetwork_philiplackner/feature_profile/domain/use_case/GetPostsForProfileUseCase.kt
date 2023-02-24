package com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.domain.repository.ProfileRepository
import com.prmto.socialnetwork_philiplackner.core.util.Resource

class GetPostsForProfileUseCase(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(
        page: Int,
        userId: String,
    ): Resource<List<Post>> {
        return profileRepository.getPostsPaged(userId = userId, page = page)
    }
}