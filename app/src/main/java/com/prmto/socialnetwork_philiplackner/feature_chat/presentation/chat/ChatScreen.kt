package com.prmto.socialnetwork_philiplackner.feature_chat.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceSmall
import com.prmto.socialnetwork_philiplackner.core.util.Screen
import com.prmto.socialnetwork_philiplackner.feature_chat.domain.model.Chat
import com.prmto.socialnetwork_philiplackner.feature_chat.presentation.components.ChatItem

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun ChatScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {

    val chats = remember {
        listOf(
            Chat(
                remoteUsername = "Philipp Lackner",
                remoteUserProfileUrl = "http://10.0.2.2:8001/profile_pictures/438a8b02-9c6e-4f3b-815f-d35fb0bb451d.jpeg",
                lastMessage = "This is the last message of the chat with Philipp",
                lastMessageFormattedTime = "19:39"
            ),
            Chat(
                remoteUsername = "Tolga Pirim",
                remoteUserProfileUrl = "http://10.0.2.2:8001/profile_pictures/438a8b02-9c6e-4f3b-815f-d35fb0bb451d.jpeg",
                lastMessage = "This is the last message of the chat with Philipp",
                lastMessageFormattedTime = "19:39"
            ),
            Chat(
                remoteUsername = "Florian",
                remoteUserProfileUrl = "http://10.0.2.2:8001/profile_pictures/438a8b02-9c6e-4f3b-815f-d35fb0bb451d.jpeg",
                lastMessage = "This is the last message of the chat with Philipp",
                lastMessageFormattedTime = "19:39"
            ),
        )
    }

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
                        onNavigate(Screen.MessagesScreen.route)
                    }
                )
            }
        }
    }

}