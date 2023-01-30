package com.prmto.socialnetwork_philiplackner.feature_post.presantation.post_detail

sealed class PostDetailEvent {
    object LikePost : PostDetailEvent()
    data class Comment(val comment: String) : PostDetailEvent()
    data class LikeComment(val commentId: String) : PostDetailEvent()
    object SharePost : PostDetailEvent()
}
