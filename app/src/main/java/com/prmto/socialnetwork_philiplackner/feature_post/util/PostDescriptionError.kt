package com.prmto.socialnetwork_philiplackner.feature_post.util

import com.prmto.socialnetwork_philiplackner.core.domain.util.Error

sealed class PostDescriptionError : Error() {
    object FieldEmpty : PostDescriptionError()
}
