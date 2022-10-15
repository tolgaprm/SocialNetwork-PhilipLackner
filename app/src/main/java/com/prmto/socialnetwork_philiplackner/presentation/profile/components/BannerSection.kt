package com.prmto.socialnetwork_philiplackner.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceSmall
import com.prmto.socialnetwork_philiplackner.presentation.util.toPx

@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    iconSize: Dp = 35.dp,
    leftIconModifier: Modifier = Modifier,
    rightIconModifier: Modifier = Modifier,
    onGitHubClick: () -> Unit = {},
    onInstagramClick: () -> Unit = {},
    onLinkedinClick: () -> Unit = {},
) {

    BoxWithConstraints(modifier = modifier) {

        Image(
            painter = painterResource(id = R.drawable.channelart),
            contentDescription = stringResource(id = R.string.banner_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()

        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = constraints.maxHeight - iconSize.toPx() * 2f
                    )
                ),
        )



        Row(
            modifier = leftIconModifier
                .height(iconSize)
                .align(Alignment.BottomStart)
                .padding(SpaceSmall)
        ) {
            Spacer(modifier = Modifier.width(SpaceSmall))
            Image(
                painter = painterResource(id = R.drawable.ic_js_logo),
                contentDescription = "JavaScript",
                modifier = Modifier.height(iconSize)
            )
            Spacer(modifier = Modifier.width(SpaceMedium))
            Image(
                painter = painterResource(id = R.drawable.ic_csharp),
                contentDescription = "CSharp"
            )
            Spacer(modifier = Modifier.width(SpaceMedium))
            Image(
                painter = painterResource(id = R.drawable.ic_kotlin),
                contentDescription = "Kotlin"
            )
        }

        Row(
            modifier = rightIconModifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(SpaceSmall)
        ) {

            IconButton(
                onClick = onGitHubClick,
                modifier = Modifier.size(iconSize)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_github),
                    contentDescription = "GitHub",
                    modifier = Modifier.size(iconSize)
                )
            }

            IconButton(
                onClick = onInstagramClick,
                modifier = Modifier.size(iconSize)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = "Instagram",
                    modifier = Modifier.size(iconSize)
                )
            }

            IconButton(
                onClick = onLinkedinClick,
                modifier = Modifier.size(iconSize)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_linkedin),
                    contentDescription = "LinkedIn",
                    modifier = Modifier.size(iconSize)
                )
            }
        }

    }
}

