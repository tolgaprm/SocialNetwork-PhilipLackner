package com.prmto.socialnetwork_philiplackner.presentation.register


import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.presentation.components.StandardTextField
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceSmall
import com.prmto.socialnetwork_philiplackner.presentation.util.Screen

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {

    Box(
        modifier = modifier
            .semantics {
                contentDescription = Screen.LoginScreen.route
            }
            .fillMaxSize()
            .padding(
                start = SpaceLarge,
                end = SpaceLarge,
                top = SpaceLarge,
                bottom = 50.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
        ) {
            Text(
                text = stringResource(id = R.string.login),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardTextField(
                text = registerViewModel.userNameText.value,
                onValueChange = {
                    registerViewModel.setUserNameText(it)
                },
                hint = stringResource(id = R.string.login_hint)
            )

            Spacer(modifier = Modifier.height(SpaceSmall))

            StandardTextField(
                text = registerViewModel.passwordText.value,
                onValueChange = {
                    registerViewModel.setPasswordText(it)
                },
                hint = stringResource(id = R.string.password_hint),
                keyboardType = KeyboardType.Password,
            )
        }

        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_an_account_yet))
                append(" ")
                val signUpText = stringResource(id = R.string.sign_up)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                    )
                ) {
                    append(signUpText)
                }

            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}

