package com.prmto.socialnetwork_philiplackner.feature_profile.data.repository

import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_profile.data.remote.ProfileApi
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.model.Profile
import com.prmto.socialnetwork_philiplackner.feature_profile.domain.repository.ProfileRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi
) : ProfileRepository {
    override suspend fun getProfile(userId: String): Resource<Profile> {
        return try {
            val response = profileApi.getProfile(userId = userId)
            if (response.successful) {
                Resource.Success(data = response.data?.toProfile())
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