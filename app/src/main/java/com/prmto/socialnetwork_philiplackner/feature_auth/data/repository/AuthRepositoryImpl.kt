package com.prmto.socialnetwork_philiplackner.feature_auth.data.repository

import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_auth.data.dto.request.CreateAccountRequest
import com.prmto.socialnetwork_philiplackner.feature_auth.data.remote.AuthApi
import com.prmto.socialnetwork_philiplackner.feature_auth.domain.repository.AuthRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        val request = CreateAccountRequest(
            email = email,
            username = username,
            password = password
        )
        return try {
            val response = authApi.register(request)
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