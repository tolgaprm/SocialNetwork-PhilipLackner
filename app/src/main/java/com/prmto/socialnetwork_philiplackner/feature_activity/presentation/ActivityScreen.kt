package com.prmto.socialnetwork_philiplackner.feature_activity.presentation

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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.models.Activity
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.feature_activity.presentation.components.ActivityItem

@Composable
fun ActivityScreen(
    onNavigateUp: () -> Unit = {},
    viewModel: ActivityViewModel = hiltViewModel()
) {

    val activitiesPagingItems = viewModel.activities.collectAsLazyPagingItems()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            title = {
                Text(
                    text = stringResource(id = R.string.your_activity),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            onNavigateUp = onNavigateUp,
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
            items(activitiesPagingItems) { activity ->
                activity?.let {
                    ActivityItem(
                        activity = Activity(
                            userId = activity.userId,
                            username = activity.username,
                            activityType = activity.activityType,
                            formattedTime = activity.formattedTime,
                            parentId = activity.parentId
                        )
                    )
                }

            }
        }
    }

}