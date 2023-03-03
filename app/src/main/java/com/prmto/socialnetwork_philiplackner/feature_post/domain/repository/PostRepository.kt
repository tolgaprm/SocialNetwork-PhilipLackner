package com.prmto.socialnetwork_philiplackner.feature_post.domain.repository

import android.net.Uri
import com.prmto.socialnetwork_philiplackner.core.domain.models.Comment
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.domain.models.UserItem
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource

interface PostRepository {

    suspend fun getPostForFollows(
        page: Int,
        pageSize: Int,
    ): Resource<List<Post>>

    suspend fun createPost(
        description: String,
        imageUri: Uri
    ): SimpleResource

    suspend fun getPostDetails(
        postId: String
    ): Resource<Post>

    suspend fun getCommentsForPost(postId: String): Resource<List<Comment>>

    suspend fun createComment(postId: String, comment: String): SimpleResource
    suspend fun likeParent(parentId: String, parentType: Int): SimpleResource

    suspend fun unLikeParent(parentId: String, parentType: Int): SimpleResource

    suspend fun getLikesForParent(parentId: String): Resource<List<UserItem>>

}