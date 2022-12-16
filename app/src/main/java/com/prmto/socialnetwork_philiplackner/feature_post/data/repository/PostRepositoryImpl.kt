package com.prmto.socialnetwork_philiplackner.feature_post.data.repository

import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.models.Post
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_post.data.data_source.paging.PostSource
import com.prmto.socialnetwork_philiplackner.feature_post.data.data_source.remote.PostApi
import com.prmto.socialnetwork_philiplackner.feature_post.data.data_source.remote.request.CreatePostRequest
import com.prmto.socialnetwork_philiplackner.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val gson: Gson
) : PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE_POST
            ),
            pagingSourceFactory = {
                PostSource(postApi)
            }
        ).flow

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
}