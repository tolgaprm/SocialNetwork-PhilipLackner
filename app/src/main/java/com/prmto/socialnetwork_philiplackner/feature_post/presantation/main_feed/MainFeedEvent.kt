package com.prmto.socialnetwork_philiplackner.feature_post.presantation.main_feed

sealed class MainFeedEvent {
    data class LikedPost(val postId: String) : MainFeedEvent()
}
