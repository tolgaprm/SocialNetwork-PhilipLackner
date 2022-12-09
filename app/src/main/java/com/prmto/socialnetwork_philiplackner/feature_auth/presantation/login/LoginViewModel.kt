package com.prmto.socialnetwork_philiplackner.feature_auth.presantation.login

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

    private val _showPassword = mutableStateOf(true)
    val showPassword: State<Boolean> get() = _showPassword

    private val _usernameError = mutableStateOf("")
    val usernameError: State<String> get() = _usernameError

    private val _passwordError = mutableStateOf("")
    val passwordError: State<String> get() = _passwordError

    fun setUserNameText(username: String) {
        _userNameText.value = username
    }

    fun setPasswordText(password: String) {
        _passwordText.value = password
    }

    fun setUserNameError(error: String) {
        _usernameError.value = error
    }


    fun setPasswordError(error: String) {
        _passwordError.value = error
    }

    fun setShowPassword(showPassword: Boolean) {
        _showPassword.value = showPassword
    }
}