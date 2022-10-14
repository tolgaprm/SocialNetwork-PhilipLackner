package com.prmto.socialnetwork_philiplackner.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.domain.models.Post
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.*
import com.prmto.socialnetwork_philiplackner.util.Constants.MAX_POST_DESCRIPTION_LINES

@Composable
fun Post(
    post: Post,
    showProfileImage: Boolean = true,
    onPostClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(
                    y = if (showProfileImage) {
                        ProfilePictureSizeMedium / 2f
                    } else 0.dp
                )
                .clip(MaterialTheme.shapes.medium)
                .shadow(5.dp)
                .background(MediumGray)
                .clickable {
                    onPostClick()
                }
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Post image",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceMedium)
            ) {
                ActionRow(
                    username = post.username,
                    modifier = Modifier.fillMaxWidth(),
                    onLikeClick = { isLiked ->

                    },
                    onCommentClick = {

                    },
                    onShareClick = {

                    },
                    onUsernameClick = { username ->

                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))

                Text(
                    text = buildAnnotatedString {
                        append(post.description)
                        withStyle(
                            SpanStyle(
                                color = HintGray,
                            )
                        ) {
                            append(
                                LocalContext.current.getString(
                                    R.string.read_more
                                )
                            )
                        }
                    },
                    style = MaterialTheme.typography.body2,
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = MAX_POST_DESCRIPTION_LINES
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.liked_by_x_people,
                            post.likeCount
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2
                    )
                    Text(
                        text = stringResource(
                            id = R.string.x_comments,
                            post.commentCount
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2
                    )
                }
            }
        }

        if (showProfileImage) {
            Image(
                painterResource(id = R.drawable.avatar),
                contentDescription = stringResource(id = R.string.profile_picture),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(ProfilePictureSizeMedium)
                    .clip(CircleShape)
                    .align(Alignment.TopCenter)
            )
        }


    }
}


@Composable
fun EngagementButtons(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    iconSize: Dp = 30.dp,
    onLikeClick: (Boolean) -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onLikeClick(!isLiked)
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = if (isLiked) Color.Red else TextWhite,
                contentDescription = if (isLiked) {
                    stringResource(R.string.unlike)
                } else stringResource(
                    R.string.like
                ),
            )
        }

        Spacer(modifier = Modifier.width(SpaceMedium))

        IconButton(
            onClick = {
                onCommentClick()
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Comment,
                tint = TextWhite,
                contentDescription = stringResource(id = R.string.comment),
            )
        }

        Spacer(modifier = Modifier.width(SpaceMedium))

        IconButton(
            onClick = {
                onShareClick()
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                tint = TextWhite,
                contentDescription = stringResource(id = R.string.share),
            )
        }
    }
}


@Composable
fun ActionRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikeClick: (Boolean) -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    username: String,
    onUsernameClick: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = username,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier
                .clickable {
                    onUsernameClick(username)
                }
        )

        EngagementButtons(
            isLiked = isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick
        )

    }
}


@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun EngagementButtonsPreview() {
    SocialNetworkPhilipLacknerTheme {
        EngagementButtons(
            isLiked = false,
            onLikeClick = { },
            onCommentClick = { },
            onShareClick = { }
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ActionsRowPreview() {

    SocialNetworkPhilipLacknerTheme {
        ActionRow(
            isLiked = true,
            onLikeClick = { },
            onCommentClick = { },
            onShareClick = { },
            username = "Tolga Pirim",
            onUsernameClick = {}
        )
    }

}


@Preview
@Composable
fun PostPreview() {
    SocialNetworkPhilipLacknerTheme {
        Post(
            post = Post(
                username = "Tolga Pirim",
                imageUrl = "",
                description = "hi Philip!!! Realy enjoying your videos and I learning a lot from those streammings.",
                profilePictureProfile = "",
                likeCount = 20,
                commentCount = 50
            )
        )
    }
}









