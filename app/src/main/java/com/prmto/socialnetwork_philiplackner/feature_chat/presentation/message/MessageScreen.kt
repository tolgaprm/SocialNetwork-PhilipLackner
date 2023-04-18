package com.prmto.socialnetwork_philiplackner.feature_chat.presentation.message


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.feature_chat.presentation.message.components.OwnMessage
import com.prmto.socialnetwork_philiplackner.feature_chat.presentation.message.components.RemoteMessage
import okio.ByteString.Companion.decodeBase64
import java.nio.charset.Charset

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalCoilApi
@Composable
fun MessageScreen(
    remoteUsername: String,
    encodedRemoteUserProfilePictureUrl: String,
    remoteUserId: String,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    viewModel: MessageViewModel = hiltViewModel(),
) {
    val decodedRemoteUserProfilePictureUrl = remember {
        encodedRemoteUserProfilePictureUrl.decodeBase64()?.string(Charset.defaultCharset())
    }
    val pagingState = viewModel.pagingState.value
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = pagingState, key2 = keyboardController) {
        viewModel.messageReceived.collect { event ->
            when (event) {
                is MessageViewModel.MessageUpdateEvent.SingleMessageReceived,
                is MessageViewModel.MessageUpdateEvent.MessagePageLoaded -> {
                    if (pagingState.items.isEmpty()) {
                        return@collect
                    }
                    lazyListState.animateScrollToItem(pagingState.items.size - 1)
                }

                is MessageViewModel.MessageUpdateEvent.MessageSent -> {
                    keyboardController?.hide()
                }
            }
        }
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
                        .data(decodedRemoteUserProfilePictureUrl)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(ProfilePictureSizeSmall)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(SpaceMedium))
                Text(
                    text = remoteUsername,
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
                verticalArrangement = Arrangement.spacedBy(SpaceLarge),
                contentPadding = PaddingValues(SpaceMedium),
                state = lazyListState
            ) {
                itemsIndexed(pagingState.items) { i, message ->
                    if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                        viewModel.loadNextMessages()
                    }
                    if (message.fromId == remoteUserId) {
                        RemoteMessage(
                            message = message.text,
                            formattedTime = message.formattedTime,
                            color = MaterialTheme.colors.surface,
                            textColor = MaterialTheme.colors.onBackground
                        )
                    } else {
                        OwnMessage(
                            message = message.text,
                            formattedTime = message.formattedTime,
                            color = DarkerGreen,
                            textColor = MaterialTheme.colors.onBackground
                        )
                    }

                }
            }
            SendTextField(
                state = viewModel.messageTextFieldState.value,
                candSendMessage = viewModel.state.value.canSendMessage,
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