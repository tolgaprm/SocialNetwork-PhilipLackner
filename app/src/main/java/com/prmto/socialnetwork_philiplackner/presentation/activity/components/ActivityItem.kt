package com.prmto.socialnetwork_philiplackner.presentation.activity.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.domain.models.Activity
import com.prmto.socialnetwork_philiplackner.domain.util.ActivityAction
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceSmall

@Composable
fun ActivityItem(
    modifier: Modifier = Modifier,
    activity: Activity
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.onSurface,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val fillerText = when (activity.actionType) {
                is ActivityAction.LikedPost ->
                    stringResource(id = R.string.liked)
                is ActivityAction.CommentedOnPost ->
                    stringResource(id = R.string.commented_on)
                is ActivityAction.FollowedYou ->
                    stringResource(id = R.string.followed_you)
            }

            val actionText = when (activity.actionType) {
                is ActivityAction.LikedPost ->
                    stringResource(id = R.string.your_post)
                is ActivityAction.CommentedOnPost ->
                    stringResource(id = R.string.your_post)
                is ActivityAction.FollowedYou -> ""
            }

            Text(
                text = buildAnnotatedString {
                    val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)
                    withStyle(boldStyle) {
                        append(activity.username)
                    }
                    append(" $fillerText ")
                    withStyle(boldStyle) {
                        append(actionText)
                    }
                },
                fontSize = 14.sp,
                color = MaterialTheme.colors.onBackground
            )

            Text(
                text = activity.formattedTime,
                textAlign = TextAlign.Right,
                fontSize = 14.sp,
                color = MaterialTheme.colors.onBackground
            )


        }
    }
}