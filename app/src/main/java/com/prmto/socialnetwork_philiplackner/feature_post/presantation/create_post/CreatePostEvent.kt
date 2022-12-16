package com.prmto.socialnetwork_philiplackner.feature_post.presantation.create_post

import android.net.Uri

sealed class CreatePostEvent {
    data class EnteredDescription(val description: String) : CreatePostEvent()
    data class PickImage(val uri: Uri?) : CreatePostEvent()
    object PostImage:CreatePostEvent()
}
