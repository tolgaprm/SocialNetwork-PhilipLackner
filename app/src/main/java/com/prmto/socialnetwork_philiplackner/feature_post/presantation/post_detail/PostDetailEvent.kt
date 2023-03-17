package com.prmto.socialnetwork_philiplackner.feature_post.presantation.post_detail

sealed class PostDetailEvent {
    object LikePost : PostDetailEvent()
    object Comment : PostDetailEvent()
    data class LikeComment(val commentId: String) : PostDetailEvent()
    data class EnteredComment(val comment: String) : PostDetailEvent()
}
