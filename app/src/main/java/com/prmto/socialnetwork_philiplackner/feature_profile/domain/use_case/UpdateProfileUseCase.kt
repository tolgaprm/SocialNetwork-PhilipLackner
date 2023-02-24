package com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case

import android.net.Uri
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.repository.ProfileRepository
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.UpdateProfileData
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.util.ProfileConstants

class UpdateProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerImageUri: Uri?
    ): SimpleResource {
        if (updateProfileData.username.isBlank()) {
            return Resource.Error(
                uiText = UiText.StringResource(
                    R.string.error_username_empty
                )
            )
        }

        if (updateProfileData.gitHubUrl.isNotEmpty()) {
            val isValidGitHubUrl =
                ProfileConstants.GITHUB_PROFILE_REGEX.matches(updateProfileData.gitHubUrl)
            if (!isValidGitHubUrl) {
                return Resource.Error(
                    uiText = UiText.StringResource(R.string.error_invalid_github_url)
                )
            }
        }

        if (updateProfileData.instagramUrl.isNotBlank()) {
            val isValidInstagramUrl =
                ProfileConstants.INSTAGRAM_PROFILE_REGEX.matches(updateProfileData.instagramUrl)
            if (!isValidInstagramUrl) {
                return Resource.Error(
                    uiText = UiText.StringResource(R.string.error_invalid_instagram_url)
                )
            }
        }

        if (updateProfileData.linkedInUrl.isNotEmpty()) {
            val isValidLinkedInUrl =
                ProfileConstants.LINKED_IN_PROFILE_REGEX.matches(updateProfileData.linkedInUrl)
            if (!isValidLinkedInUrl) {
                return Resource.Error(
                    uiText = UiText.StringResource(R.string.error_invalid_linkedin_url)
                )
            }
        }


        return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerImageUri,
        )
    }
}