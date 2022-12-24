package com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case

import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Skill
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.util.ProfileConstants.MAX_SELECTED_SkILL_COUNT

class SetSkillsSelectedUseCase {

    operator fun invoke(
        selectedSkills: List<Skill>,
        skillToToggle: Skill
    ): Resource<List<Skill>> {
        val skillInList = selectedSkills.find { it.name == skillToToggle.name }
        if (skillInList != null) {
            return Resource.Success(selectedSkills - skillInList)
        }
        return if (selectedSkills.size >= MAX_SELECTED_SkILL_COUNT) {
            Resource.Error(UiText.StringResource(R.string.error_max_skills_selected))
        } else {
            Resource.Success(selectedSkills + skillToToggle)
        }
    }
}