package com.prmto.socialnetwork_philiplackner.presentation.person_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.domain.models.User
import com.prmto.socialnetwork_philiplackner.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.presentation.components.UserProfileItem
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceMedium

@ExperimentalMaterialApi
@Composable
fun PersonListScreen(
    navController: NavController
) {

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolbar(
            navController = navController,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.liked_by),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SpaceMedium),
            contentPadding = PaddingValues(SpaceMedium)
        ) {
            items(10) {
                UserProfileItem(
                    user = User(
                        profilePictureUrl = "",
                        username = "Tolga Pirim",
                        description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed\n" +
                                "diam nonumy eirmod tempor invidunt ut labore et dolore \n" +
                                "magna aliquyam erat, sed diam voluptua",
                        followerCount = 1455,
                        followingCount = 25,
                        postCount = 54
                    ),
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