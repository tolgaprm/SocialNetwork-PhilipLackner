package com.prmto.socialnetwork_philiplackner.domain.util

sealed class ActivityAction {
    object LikedPost : ActivityAction()
    object CommentedOnPost : ActivityAction()
}
