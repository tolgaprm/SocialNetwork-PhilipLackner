package com.prmto.socialnetwork_philiplackner.feature_auth.domain.repository

import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource

interface AuthRepository {

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource

    suspend fun login(
        email: String,
        password: String
    ): SimpleResource
}