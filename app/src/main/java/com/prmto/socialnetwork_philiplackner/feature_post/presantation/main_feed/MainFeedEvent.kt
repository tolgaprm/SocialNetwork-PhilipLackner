package com.prmto.socialnetwork_philiplackner.feature_post.presantation.main_feed

sealed class MainFeedEvent {
    object LoadMorePosts : MainFeedEvent()
    object LoadedPage : MainFeedEvent()
}
