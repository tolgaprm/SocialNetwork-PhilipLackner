package com.prmto.socialnetwork_philiplackner.presentation.create_post

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.presentation.components.StandardTextField
import com.prmto.socialnetwork_philiplackner.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceSmall
import com.prmto.socialnetwork_philiplackner.presentation.util.states.StandardTextFieldState

@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        StandardToolbar(
            navController = navController,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.create_a_new_post),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            navActions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.create_post),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceLarge),
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.choose_image),
                    tint = MaterialTheme.colors.onBackground
                )
            }

            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.descriptionState.value.text,
                error = viewModel.descriptionState.value.error,
                hint = stringResource(id = R.string.description),
                singleLine = false,
                maxLength = Int.MAX_VALUE,
                maxlines = 5,
                onValueChange = {
                    viewModel.setDescriptionState(
                        StandardTextFieldState(text = it)
                    )
                }
            )

            Spacer(modifier = Modifier.height(SpaceLarge))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.post),
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
        }
    }
}