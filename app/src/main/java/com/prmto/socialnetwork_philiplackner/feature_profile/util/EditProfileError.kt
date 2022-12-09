package com.prmto.socialnetwork_philiplackner.feature_profile.util

import com.prmto.socialnetwork_philiplackner.core.util.Error

sealed class EditProfileError : Error() {
    object FieldEmpty : EditProfileError()
}
