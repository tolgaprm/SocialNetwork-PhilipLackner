package com.prmto.socialnetwork_philiplackner.feature_profile.util

import com.prmto.socialnetwork_philiplackner.core.domain.util.Error

sealed class EditProfileError : Error() {
    object FieldEmpty : EditProfileError()
}
