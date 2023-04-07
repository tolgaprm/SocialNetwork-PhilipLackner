package com.prmto.socialnetwork_philiplackner.feature_chat.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.core.util.Screen

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun ChatScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ChatViewModel = hiltViewModel(),
) {

    val chats = viewModel.state.value.chats
    val isLoading = viewModel.state.value.isLoading
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceMedium),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SpaceLarge)
        ) {
            items(chats) { chat ->
                ChatItem(
                    item = chat,
                    onItemClick = {
                        onNavigate(
                            Screen.MessagesScreen.route + "/${chat.chatId}/${chat.remoteUserId}/${chat.remoteUsername}/${
                                android.util.Base64.encodeToString(
                                    chat.remoteUserProfileUrl.encodeToByteArray(),
                                    0
                                )
                            }"
                        )
                    }
                )
            }
        }
    }

}