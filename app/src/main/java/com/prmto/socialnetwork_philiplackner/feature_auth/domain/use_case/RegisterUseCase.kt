package com.prmto.socialnetwork_philiplackner.feature_auth.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.feature_auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        return repository.register(
            email = email.trim(),
            username = username.trim(),
            password = password.trim()
        )
    }
}