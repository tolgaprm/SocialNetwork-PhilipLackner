package com.prmto.socialnetwork_philiplackner.core.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.models.User
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceSmall

@ExperimentalMaterialApi
@Composable
fun UserProfileItem(
    modifier: Modifier = Modifier,
    user: User,
    context: Context,
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
            AsyncImage(
                model = ImageRequest.Builder(
                    context = context
                ).data(user.profilePictureUrl)
                    .build(),
                modifier = Modifier
                    .size(ProfilePictureSizeSmall)
                    .clip(CircleShape),
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
                    text = user.userName,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = user.bio,
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