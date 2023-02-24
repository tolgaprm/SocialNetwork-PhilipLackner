package com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.domain.repository.ProfileRepository
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Skill

class GetSkillsUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Resource<List<Skill>> {
        return repository.getSkills()
    }
}