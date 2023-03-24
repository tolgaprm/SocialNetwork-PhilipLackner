package com.prmto.socialnetwork_philiplackner.feature_chat.presentation.message


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prmto.socialnetwork_philiplackner.core.presentation.components.SendTextField
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.DarkerGreen
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Message
import com.prmto.socialnetwork_philiplackner.feature_chat.presentation.message.components.OwnMessage
import com.prmto.socialnetwork_philiplackner.feature_chat.presentation.message.components.RemoteMessage

@ExperimentalCoilApi
@Composable
fun MessageScreen(
    chatId: String,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    viewModel: MessageViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val messages = remember {
        listOf(
            Message(
                fromId = "",
                toId = "",
                text = "Hello World!",
                formattedTime = "11:29",
                chatId = "",
                id = ""
            ),
            Message(
                fromId = "",
                toId = "",
                text = "How are you?",
                formattedTime = "11:29",
                chatId = "",
                id = ""
            ),
            Message(
                fromId = "",
                toId = "",
                text = "Hello World!",
                formattedTime = "11:29",
                chatId = "",
                id = ""
            ),
        )
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("http://10.0.2.2:8001/profile_pictures/438a8b02-9c6e-4f3b-815f-d35fb0bb451d.jpeg")
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(ProfilePictureSizeSmall)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(SpaceMedium))
                Text(
                    text = "Tolga Pirim",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            }
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(SpaceMedium),
                contentPadding = PaddingValues(SpaceMedium)
            ) {
                items(messages) { message ->
                    RemoteMessage(
                        message = message.text,
                        formattedTime = message.formattedTime,
                        color = MaterialTheme.colors.surface,
                        textColor = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    OwnMessage(
                        message = message.text,
                        formattedTime = message.formattedTime,
                        color = DarkerGreen,
                        textColor = MaterialTheme.colors.onBackground
                    )
                }
            }
            SendTextField(
                state = viewModel.messageTextFieldState.value,
                onValueChange = {
                    viewModel.onEvent(MessageEvent.EnteredMessage(it))
                },
                onSend = {
                    viewModel.onEvent(MessageEvent.SendMessage)
                },
                hint = stringResource(id = com.prmto.socialnetwork_philiplackner.R.string.enter_a_message)
            )
        }
    }
}