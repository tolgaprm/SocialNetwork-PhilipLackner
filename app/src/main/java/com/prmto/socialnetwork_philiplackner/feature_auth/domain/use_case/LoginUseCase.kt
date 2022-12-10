package com.prmto.socialnetwork_philiplackner.feature_auth.domain.use_case

import com.prmto.socialnetwork_philiplackner.feature_auth.domain.models.LoginResult
import com.prmto.socialnetwork_philiplackner.feature_auth.domain.repository.AuthRepository
import com.prmto.socialnetwork_philiplackner.feature_auth.util.AuthError

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): LoginResult {
        val emailError = if (email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if (password.isBlank()) AuthError.FieldEmpty else null

        if (emailError != null || passwordError != null) {
            return LoginResult(emailError = emailError, passwordError = passwordError)
        }

        return LoginResult(
            result = repository.login(email = email.trim(), password = password)
        )
}
}
