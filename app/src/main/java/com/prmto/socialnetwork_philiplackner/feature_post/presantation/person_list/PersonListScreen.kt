package com.prmto.socialnetwork_philiplackner.feature_post.presantation.person_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.presentation.components.UserProfileItem
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.presentation.util.asString
import com.prmto.socialnetwork_philiplackner.core.util.Screen
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun PersonListScreen(
    scaffoldState: ScaffoldState,
    viewModel: PersonListViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {

    val context = LocalContext.current
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.uiText.asString(context))
                }
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolbar(
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.liked_by),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            onNavigateUp = onNavigateUp
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(SpaceMedium),
                contentPadding = PaddingValues(SpaceMedium)
            ) {
                items(state.users) { user ->
                    UserProfileItem(
                        user = user,
                        ownUserId = viewModel.ownUserId.value,
                        actionIcon = {
                            Icon(
                                imageVector = if (user.isFollowing) {
                                    Icons.Default.PersonRemove
                                } else {
                                    Icons.Default.PersonAdd
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground
                            )
                        },
                        onItemClick = {
                           onNavigate(Screen.ProfileScreen.route + "?userId=${user.userId}")
                        },
                        onActionItemClick = {
                            viewModel.onEvent(
                                PersonListEvent.ToggleFollowState(
                                    userId = user.userId,
                                    isFollowing = user.isFollowing
                                )
                            )
                        },
                        context = context
                    )
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}