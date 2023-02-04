package com.prmto.socialnetwork_philiplackner.feature_post.util

sealed class CommentError : com.prmto.socialnetwork_philiplackner.core.util.Error() {
    object FieldEmpty : CommentError()
}
