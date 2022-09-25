package com.prmto.socialnetwork_philiplackner.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _userNameText = mutableStateOf("")
    val userNameText: State<String> get() = _userNameText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> get() = _passwordText

    fun setUserNameText(username: String) {
        _userNameText.value = username
    }

    fun setPasswordText(password: String) {
        _passwordText.value = password
    }
}