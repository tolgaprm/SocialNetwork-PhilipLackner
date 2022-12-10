package com.prmto.socialnetwork_philiplackner.feature_auth.presantation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.socialnetwork_philiplackner.core.domain.states.PasswordTextFieldState
import com.prmto.socialnetwork_philiplackner.core.domain.states.StandardTextFieldState
import com.prmto.socialnetwork_philiplackner.core.util.Resource
import com.prmto.socialnetwork_philiplackner.core.util.Screen
import com.prmto.socialnetwork_philiplackner.core.util.UiText
import com.prmto.socialnetwork_philiplackner.feature_auth.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> get() = _emailState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> get() = _passwordState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(text = event.email)
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(text = event.password)
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _passwordState.value =
                    _passwordState.value.copy(isPasswordVisible = !_passwordState.value.isPasswordVisible)
            }
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    _isLoading.value = true
                    val loginResult = loginUseCase(
                        email = emailState.value.text,
                        password = passwordState.value.text
                    )
                    _isLoading.value = false
                    if (loginResult.emailError != null) {
                        _emailState.value = _emailState.value.copy(
                            error = loginResult.emailError
                        )
                    }
                    if (loginResult.passwordError != null) {
                        _passwordState.value = _passwordState.value.copy(
                            error = loginResult.passwordError
                        )
                    }
                    when (loginResult.result) {
                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.Navigate(Screen.MainFeedScreen.route))
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.SnackbarEvent(
                                    loginResult.result.uiText ?: UiText.unknownError()
                                )
                            )
                        }
                        null -> {
                            _isLoading.value = false
                        }
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class SnackbarEvent(val uiText: UiText) : UiEvent()
        data class Navigate(val route: String) : UiEvent()
    }
}