package com.prmto.socialnetwork_philiplackner.feature_auth.util

import com.prmto.socialnetwork_philiplackner.core.domain.util.Error

sealed class AuthError : Error() {
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword : AuthError()
}
