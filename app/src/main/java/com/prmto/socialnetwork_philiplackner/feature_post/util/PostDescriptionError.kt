package com.prmto.socialnetwork_philiplackner.feature_post.util

import com.prmto.socialnetwork_philiplackner.core.util.Error

sealed class PostDescriptionError : Error() {
    object FieldEmpty : PostDescriptionError()
}
