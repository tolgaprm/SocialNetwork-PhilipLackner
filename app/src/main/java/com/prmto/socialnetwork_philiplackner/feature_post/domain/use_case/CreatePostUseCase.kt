package com.prmto.socialnetwork_philiplackner.feature_post.domain.use_case

import android.net.Uri
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository

class CreatePostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        description: String,
        imageUri: Uri
    ): SimpleResource {
        return repository.createPost(
            description = description,
            imageUri = imageUri
        )
    }
}