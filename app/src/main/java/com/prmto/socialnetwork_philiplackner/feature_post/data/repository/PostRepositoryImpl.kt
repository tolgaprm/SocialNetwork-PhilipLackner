package com.prmto.socialnetwork_philiplackner.feature_post.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.models.Comment
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.domain.models.UserItem
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_post.data.remote.PostApi
import com.prmto.socialnetwork_philiplackner.feature_post.data.remote.request.CreateCommentRequest
import com.prmto.socialnetwork_philiplackner.feature_post.data.remote.request.CreatePostRequest
import com.prmto.socialnetwork_philiplackner.feature_post.data.remote.request.LikeUpdateRequest
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val gson: Gson
) : PostRepository {

    override suspend fun getPostForFollows(page: Int, pageSize: Int): Resource<List<Post>> {
        return try {
            val posts = postApi.getPostsForFollows(
                page = page,
                pageSize = pageSize
            )
            Resource.Success(data = posts)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }

    override suspend fun createPost(
        description: String,
        imageUri: Uri
    ): SimpleResource {
        val request = CreatePostRequest(description = description)
        val file = imageUri.toFile()

        return try {
            val response = postApi.createPost(
                postData = MultipartBody.Part
                    .createFormData(
                        "post_data",
                        gson.toJson(request)
                    ),
                postImage = MultipartBody.Part.createFormData(
                    name = "post_image",
                    filename = file.name,
                    body = file.asRequestBody()
                )
            )
            if (response.successful) {
                Resource.Success(data = Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }

    override suspend fun getPostDetails(postId: String): Resource<Post> {
        return try {
            val response = postApi.getPostDetails(
                postId = postId
            )
            if (response.successful) {
                Resource.Success(data = response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }

    override suspend fun getCommentsForPost(postId: String): Resource<List<Comment>> {
        return try {
            val comments = postApi.getCommentsForPost(postId = postId).map {
                it.toComment()
            }
            Resource.Success(data = comments)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }

    override suspend fun createComment(postId: String, comment: String): SimpleResource {
        return try {
            val response = postApi.createComment(
                request = CreateCommentRequest(
                    comment = comment,
                    postId = postId
                )
            )
            if (response.successful) {
                Resource.Success(data = Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }

    override suspend fun likeParent(parentId: String, parentType: Int): SimpleResource {
        return try {
            val response = postApi.likeParent(
                request = LikeUpdateRequest(
                    parentId = parentId,
                    parentType = parentType
                )
            )
            if (response.successful) {
                Resource.Success(data = Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }

    override suspend fun unLikeParent(parentId: String, parentType: Int): SimpleResource {
        return try {
            val response = postApi.unlikeParent(
                parentId = parentId,
                parentType = parentType
            )
            if (response.successful) {
                Resource.Success(data = Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.unknownError())
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }

    override suspend fun getLikesForParent(parentId: String): Resource<List<UserItem>> {
        return try {
            val response = postApi.getLikesForParent(
                parentId = parentId
            )
            Resource.Success(data = response.map { it.toUserItem() })
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }

    override suspend fun deletePost(postId: String): SimpleResource {
        return try {
            postApi.deletePost(postId = postId)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_someting_went_wrong)
            )
        }
    }


}