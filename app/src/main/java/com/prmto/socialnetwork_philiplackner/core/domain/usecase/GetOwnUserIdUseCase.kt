package com.prmto.socialnetwork_philiplackner.core.domain.usecase

import android.content.SharedPreferences
import com.prmto.socialnetwork_philiplackner.core.util.Constants

class GetOwnUserIdUseCase(
    private val sharedPreferences: SharedPreferences
) {

    operator fun invoke(): String {
        return sharedPreferences.getString(Constants.KEY_USER_ID, "") ?: ""
    }
}