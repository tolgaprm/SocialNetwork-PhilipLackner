package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardTextField
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.presentation.components.UserProfileItem
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.presentation.util.asString
import com.prmto.socialnetwork_philiplackner.core.util.Screen
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {

    val state = viewModel.searchState.value
    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            StandardToolbar(
                showBackArrow = true,
                title = {
                    Text(
                        text = stringResource(id = R.string.search_for_users),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                onNavigateUp = onNavigateUp
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceLarge)
            ) {
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.searchFieldState.value.text,
                    error = "",
                    leadingIcon = Icons.Default.Search,
                    hint = stringResource(id = R.string.search_hint),
                    onValueChange = {
                        viewModel.onEvent(
                            event = SearchEvent.Query(query = it)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(SpaceLarge))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(SpaceMedium)
                ) {
                    items(state.userItems) { user ->
                        var icon by remember { mutableStateOf(Icons.Default.PersonAdd) }
                        UserProfileItem(
                            user = user,
                            context = LocalContext.current,
                            onItemClick = {
                                onNavigate(Screen.ProfileScreen.route + "?userId=${user.userId}")
                            },
                            actionIcon = {
                                icon = if (user.isFollowing) {
                                    Icons.Default.PersonRemove
                                } else {
                                    Icons.Default.PersonAdd
                                }
                                IconButton(onClick = {
                                    viewModel.onEvent(
                                        SearchEvent.ToggleFollowState(
                                            userId = user.userId,
                                            isFollowing = user.isFollowing
                                        )
                                    )
                                }) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.onBackground
                                    )
                                }

                            }
                        )
                    }
                }
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}