package com.prmto.socialnetwork_philiplackner.feature_post.presantation.post_detail

import com.prmto.socialnetwork_philiplackner.core.domain.models.Comment
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post

data class PostDetailState(
    val post: Post? = null,
    val isLoadingPost: Boolean = false,
    val isLoadingComments: Boolean = false,
    val comments: List<Comment> = emptyList()

)
