package com.prmto.socialnetwork_philiplackner.feature_auth.data.remote.data_source.remote

import com.prmto.socialnetwork_philiplackner.core.data.dto.response.BasicApiResponse
import com.prmto.socialnetwork_philiplackner.feature_auth.data.remote.data_source.remote.request.CreateAccountRequest
import com.prmto.socialnetwork_philiplackner.feature_auth.data.remote.data_source.remote.request.LoginRequest
import com.prmto.socialnetwork_philiplackner.feature_auth.data.remote.data_source.remote.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): BasicApiResponse<Unit>

    @POST("/api/user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): BasicApiResponse<AuthResponse>

    @GET("/api/user/authenticate")
    suspend fun authenticate()

    companion object {
        val BASE_URL = "http://10.0.2.2:8001"
    }
}