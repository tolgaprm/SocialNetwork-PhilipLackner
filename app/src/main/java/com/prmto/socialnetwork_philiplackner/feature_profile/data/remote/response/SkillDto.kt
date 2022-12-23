package com.prmto.socialnetwork_philiplackner.feature_profile.data.remote.response

import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Skill

data class SkillDto(
    val name: String,
    val imageUrl: String
) {
    fun toSkill(): Skill {
        return Skill(
            name = name,
            imageUrl = imageUrl
        )
    }
}
