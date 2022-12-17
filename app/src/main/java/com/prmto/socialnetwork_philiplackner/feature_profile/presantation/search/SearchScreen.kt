package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.models.User
import com.prmto.socialnetwork_philiplackner.core.domain.states.StandardTextFieldState
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardTextField
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.presentation.components.UserProfileItem
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.core.util.Screen

@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
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
                text = viewModel.searchState.value.text,
                error = "",
                leadingIcon = Icons.Default.Search,
                hint = stringResource(id = R.string.search_hint),
                onValueChange = {
                    viewModel.setSearchState(
                        state = StandardTextFieldState(text = it)
                    )
                }
            )

            Spacer(modifier = Modifier.height(SpaceLarge))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(SpaceMedium)
            ) {
                items(10) {
                    UserProfileItem(
                        user = User(
                            userId = "6367b3cec4e4cc0ccf405035",
                            profilePictureUrl = "",
                            username = "Tolga Pirim",
                            bio = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed\n" +
                                    "diam nonumy eirmod tempor invidunt ut labore et dolore \n" +
                                    "magna aliquyam erat, sed diam voluptua",
                            followerCount = 1455,
                            followingCount = 25,
                            postCount = 54
                        ),
                        onItemClick = {
                            onNavigate(Screen.ProfileScreen.route + "?userId=6367b3cec4e4cc0ccf405035")
                        },
                        actionIcon = {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                    )
                }
            }
        }
    }
}