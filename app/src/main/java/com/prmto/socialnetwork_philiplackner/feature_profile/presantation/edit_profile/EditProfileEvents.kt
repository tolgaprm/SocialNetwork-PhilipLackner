package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.edit_profile

import android.net.Uri

sealed class EditProfileEvents {
    data class EnteredUserName(val value: String) : EditProfileEvents()
    data class EnteredGitHubUrl(val value: String) : EditProfileEvents()
    data class EnteredInstagramUrl(val value: String) : EditProfileEvents()
    data class EnteredLinkedInUrl(val value: String) : EditProfileEvents()
    data class EnteredBio(val value: String) : EditProfileEvents()

    data class CropProfileImage(val uri: Uri?) : EditProfileEvents()
    data class CropBannerImage(val uri: Uri?) : EditProfileEvents()

    data class SetSkillSelected(val skill: String, val selected: Boolean) : EditProfileEvents()

    object UpdateProfile : EditProfileEvents()
}
