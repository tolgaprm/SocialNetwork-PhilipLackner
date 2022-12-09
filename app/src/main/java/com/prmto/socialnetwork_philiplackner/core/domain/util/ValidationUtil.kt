package com.prmto.socialnetwork_philiplackner.core.domain.util

import android.util.Patterns
import com.prmto.socialnetwork_philiplackner.core.util.Constants
import com.prmto.socialnetwork_philiplackner.feature_auth.util.AuthError

object ValidationUtil {

    fun validateEmail(email: String): AuthError? {
        val trimmedEmail = email.trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            return AuthError.InvalidEmail
        }
        if (trimmedEmail.isBlank()) {
            return AuthError.FieldEmpty
        }
        return null
    }

    fun validateUsername(username: String): AuthError? {
        val trimmedUsername = username.trim()
        if (trimmedUsername.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            return AuthError.InputTooShort
        }
        return null
    }

    fun validatePassword(password: String): AuthError? {
        if (password.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (password.length < Constants.MIN_PASSWORD_LENGTH) {
            return AuthError.InputTooShort
        }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if (!capitalLettersInPassword || !numberInPassword) {
            return AuthError.InvalidPassword
        }
        return null
    }
}