package com.prmto.socialnetwork_philiplackner.core.util

import com.prmto.socialnetwork_philiplackner.core.domain.models.Post

class DefaultPostLiker : PostLiker {
    override suspend fun toggleLike(
        posts: List<Post>,
        parentId: String,
        onRequest: suspend (Boolean) -> SimpleResource,
        onStateUpdated: (List<Post>) -> Unit,
    ) {
        val post = posts.find { it.id == parentId }
        val currentlyLiked = post?.isLiked == true
        val currentlyLikeCount = post?.likeCount ?: 0

        updateLikeState(
            posts = posts,
            parentId = parentId,
            currentlyLiked = currentlyLiked,
            onStateUpdated = onStateUpdated
        )

        when (onRequest(currentlyLiked)) {
            is Resource.Success -> Unit
            is Resource.Error -> {
                defaultLikeStateWhenOnError(
                    posts = posts,
                    parentId = parentId,
                    currentlyLiked = currentlyLiked,
                    currentlyLikeCount = currentlyLikeCount,
                    onStateUpdated = onStateUpdated
                )
            }
        }
    }

    private fun updateLikeState(
        posts: List<Post>,
        parentId: String,
        currentlyLiked: Boolean,
        onStateUpdated: (List<Post>) -> Unit,
    ) {
        val newPosts = posts.map { post ->
            if (post.id == parentId) {
                post.copy(
                    isLiked = !post.isLiked,
                    likeCount = if (currentlyLiked) {
                        post.likeCount - 1
                    } else post.likeCount + 1
                )
            } else post
        }
        onStateUpdated(newPosts)
    }

    private fun defaultLikeStateWhenOnError(
        posts: List<Post>,
        parentId: String,
        currentlyLiked: Boolean,
        currentlyLikeCount: Int,
        onStateUpdated: (List<Post>) -> Unit,
    ) {
        val oldPosts = posts.map { post ->
            if (post.id == parentId) {
                post.copy(
                    isLiked = currentlyLiked,
                    likeCount = currentlyLikeCount
                )
            } else post
        }
        onStateUpdated(oldPosts)
    }
}
