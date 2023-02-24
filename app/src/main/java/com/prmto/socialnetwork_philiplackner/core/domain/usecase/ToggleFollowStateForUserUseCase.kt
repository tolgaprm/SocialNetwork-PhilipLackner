package com.prmto.socialnetwork_philiplackner.core.domain.usecase

import com.prmto.socialnetwork_philiplackner.core.domain.repository.ProfileRepository
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import javax.inject.Inject

class ToggleFollowStateForUserUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String, isFollowing: Boolean): SimpleResource {
      return  if (isFollowing){
            repository.unFollowUser(userId = userId)
        }else{
            repository.followUser(userId = userId)
        }
    }
}