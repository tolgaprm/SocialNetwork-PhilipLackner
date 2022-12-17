package com.prmto.socialnetwork_philiplackner.feature_auth.presantation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.states.PasswordTextFieldState
import com.prmto.socialnetwork_philiplackner.core.domain.states.StandardTextFieldState
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_auth.domain.use_case.RegisterUseCase
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
                register()
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            _emailState.value = _emailState.value.copy(error = null)
            _usernameState.value = _usernameState.value.copy(error = null)
            _passwordState.value = _passwordState.value.copy(error = null)

            _registerState.value = RegisterState(isLoading = true)
            val registerResult = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text
            )
            if (registerResult.emailError != null) {
                _emailState.value = _emailState.value.copy(
                    error = registerResult.emailError
                )
            }

            if (registerResult.usernameError != null) {
                _usernameState.value = _usernameState.value.copy(
                    error = registerResult.usernameError
                )
            }

            if (registerResult.passwordError != null) {
                _passwordState.value = _passwordState.value.copy(
                    error = registerResult.passwordError
                )
            }

            when (registerResult.result) {
                is Resource.Success -> {
                    _registerState.value = RegisterState(isLoading = false)
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.success_registration)
                        ),
                    )
                    _registerState.value = RegisterState(isLoading = false)
                    _emailState.value = StandardTextFieldState()
                    _usernameState.value = StandardTextFieldState()
                    _passwordState.value = PasswordTextFieldState()
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            registerResult.result.uiText ?: UiText.unknownError()
                        ),
                    )
                    _registerState.value = RegisterState(isLoading = false)
                }
                null -> {
                    _registerState.value = RegisterState(isLoading = false)
                }
            }
        }
    }
}