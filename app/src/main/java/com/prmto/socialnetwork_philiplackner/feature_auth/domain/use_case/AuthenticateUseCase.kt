package com.prmto.socialnetwork_philiplackner.feature_auth.domain.use_case

import com.prmto.socialnetwork_philiplackner.core.util.SimpleResource
import com.prmto.socialnetwork_philiplackner.feature_auth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke():SimpleResource {
        return repository.authenticate()
    }
}