package com.prmto.socialnetwork_philiplackner.feature_auth.presantation.register

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.states.PasswordTextFieldState
import com.prmto.socialnetwork_philiplackner.core.domain.states.StandardTextFieldState
import com.prmto.socialnetwork_philiplackner.core.util.Constants.MIN_PASSWORD_LENGTH
import com.prmto.socialnetwork_philiplackner.core.util.Constants.MIN_USERNAME_LENGTH
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_auth.domain.use_case.RegisterUseCase
import com.prmto.socialnetwork_philiplackner.feature_auth.util.AuthError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> get() = _emailState

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> get() = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> get() = _passwordState

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredUsername -> {
                _usernameState.value = _usernameState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.TogglePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
            is RegisterEvent.Register -> {
                validateUsername(usernameState.value.text)
                validateEmail(emailState.value.text)
                validatePassword(passwordState.value.text)
                registerIfNoErrors()
            }
        }
    }

    private fun registerIfNoErrors() {
        if (emailState.value.error != null || usernameState.value.error != null || passwordState.value.error != null) {
            return
        }
        _registerState.value = RegisterState(isLoading = true)
        viewModelScope.launch {
            val result = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(
                            UiText.StringResource(R.string.success_registration)
                        ),
                    )
                    _registerState.value = RegisterState(isLoading = false)
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(
                            result.uiText ?: UiText.unknownError()
                        ),
                    )
                    _registerState.value = RegisterState(isLoading = false)
                }
            }
        }
    }

    private fun validateUsername(username: String) {
        val trimmedUsername = username.trim()
        if (trimmedUsername.isBlank()) {
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (trimmedUsername.length < MIN_USERNAME_LENGTH) {
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.InputTooShort
            )
            return
        }
        _usernameState.value = _usernameState.value.copy(error = null)
    }

    private fun validateEmail(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.InvalidEmail
            )
            return
        }
        _emailState.value = _emailState.value.copy(error = null)
    }

    private fun validatePassword(password: String) {
        if (password.isBlank()) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InvalidPassword
            )
            return
        }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if (!capitalLettersInPassword || !numberInPassword) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InvalidPassword
            )
            return
        }
        _passwordState.value = _passwordState.value.copy(error = null)
    }

    sealed class UiEvent {
        data class SnackbarEvent(val uiText: UiText) : UiEvent()
    }
}