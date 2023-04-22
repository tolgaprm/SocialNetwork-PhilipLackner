package com.prmto.socialnetwork_philiplackner.feature_post.presantation.main_feed

import com.prmto.socialnetwork_philiplackner.core.domain.models.Post

sealed class MainFeedEvent {
    data class LikedPost(val postId: String) : MainFeedEvent()
    data class DeletePost(val postId: String) : MainFeedEvent()
}
