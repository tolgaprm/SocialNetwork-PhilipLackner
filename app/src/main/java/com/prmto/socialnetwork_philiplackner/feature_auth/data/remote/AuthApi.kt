package com.prmto.socialnetwork_philiplackner.feature_auth.data.remote

import com.prmto.socialnetwork_philiplackner.core.data.dto.response.BasicApiResponse
import com.prmto.socialnetwork_philiplackner.feature_auth.data.dto.request.CreateAccountRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): BasicApiResponse

    companion object {
        val BASE_URL = "http://10.0.2.2:8001"
    }
}