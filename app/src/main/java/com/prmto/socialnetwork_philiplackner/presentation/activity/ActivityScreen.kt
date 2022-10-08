package com.prmto.socialnetwork_philiplackner.presentation.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.domain.models.Activity
import com.prmto.socialnetwork_philiplackner.domain.util.ActivityAction
import com.prmto.socialnetwork_philiplackner.domain.util.DateFormatUtil
import com.prmto.socialnetwork_philiplackner.presentation.activity.components.ActivityItem
import com.prmto.socialnetwork_philiplackner.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceMedium
import kotlin.random.Random

@Composable
fun ActivityScreen(
    navController: NavController,
    viewModel: ActivityViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_activity),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = SpaceMedium, start = SpaceMedium, end = SpaceMedium,
                bottom = 56.dp
            ),
            verticalArrangement = Arrangement.spacedBy(SpaceMedium)
        ) {
            items(20) {
                ActivityItem(
                    activity = Activity(
                        username = "Tolga Pirim",
                        actionType = if (Random.nextInt(2) == 0) {
                            ActivityAction.LikedPost
                        } else {
                            ActivityAction.CommentedOnPost
                        },
                        formattedTime = DateFormatUtil.timestampsToFormattedString(
                            timestamp = System.currentTimeMillis(),
                            pattern = "MMM dd, HH:mm"
                        )
                    )
                )
            }
        }
    }

}