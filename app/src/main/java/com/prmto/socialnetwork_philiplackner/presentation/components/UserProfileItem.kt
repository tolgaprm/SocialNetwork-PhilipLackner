package com.prmto.socialnetwork_philiplackner.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.domain.models.User
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.ProfilePictureSizeSmall
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceSmall

@ExperimentalMaterialApi
@Composable
fun UserProfileItem(
    modifier: Modifier = Modifier,
    user: User,
    onItemClick: () -> Unit = {},
    onActionItemClick: () -> Unit = {},
    actionIcon: @Composable () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onItemClick,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .size(ProfilePictureSizeSmall)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = stringResource(id = R.string.profile_picture),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(SpaceSmall))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8f)
            ) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = user.description,
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 15.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(SpaceSmall))
            IconButton(
                onClick = onActionItemClick,
                modifier = Modifier.weight(1f)
            ) {
                actionIcon()
            }
        }
    }

}