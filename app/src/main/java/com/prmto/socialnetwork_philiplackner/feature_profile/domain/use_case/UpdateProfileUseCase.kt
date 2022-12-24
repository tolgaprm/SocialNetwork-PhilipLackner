package com.prmto.socialnetwork_philiplackner.feature_profile.domain.use_case

import android.net.Uri
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.UpdateProfileData
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.repository.ProfileRepository

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
        val isValidGitHubUrl = updateProfileData.gitHubUrl.startsWith("https://github.com")
                || updateProfileData.gitHubUrl.startsWith("http://github.com")
                || updateProfileData.gitHubUrl.startsWith("github.com")

        val gitHubPattern ="[http|https]".toRegex()

        if (!isValidGitHubUrl) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_invalid_github_url)
            )
        }
        return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerImageUri,
        )
    }
}