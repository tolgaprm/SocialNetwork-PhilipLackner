package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

import android.net.Uri
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository

class CreatePostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        description: String,
        imageUri: Uri?
    ): SimpleResource {
        if (imageUri == null) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_no_image_picked)
            )
        }
        if (description.isBlank()) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_decsription_blank)
            )
        }
        return repository.createPost(
            description = description,
            imageUri = imageUri
        )
    }
}