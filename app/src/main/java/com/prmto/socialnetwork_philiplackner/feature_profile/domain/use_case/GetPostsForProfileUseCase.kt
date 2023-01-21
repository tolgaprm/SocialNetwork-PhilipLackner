package com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case

import androidx.paging.PagingData
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetPostsForProfileUseCase(
    private val profileRepository: ProfileRepository
) {

    operator fun invoke(userId: String): Flow<PagingData<Post>> {
        return profileRepository.getPostsPaged(userId = userId)
    }
}