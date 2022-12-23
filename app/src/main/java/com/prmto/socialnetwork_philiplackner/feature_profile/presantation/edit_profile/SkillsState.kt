package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.edit_profile

import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Skill

data class SkillsState(
    val skills: List<Skill> = emptyList(),
    val selectedSkills: List<Skill> = emptyList()
)
