package com.prmto.socialnetwork_philiplackner.feature_chat.presentation.message.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium

@Composable
fun OwnMessage(
    message: String,
    formattedTime: String,
    modifier: Modifier = Modifier,
    color: Color = Color.DarkGray,
    textColor: Color = MaterialTheme.colors.onSurface,
    triangleWidth: Dp = 30.dp,
    triangleHeight: Dp = 30.dp,
) {
    val cornerRadius = MaterialTheme.shapes.medium.bottomEnd
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = formattedTime,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.align(Alignment.Bottom)
        )
        Spacer(modifier = Modifier.width(SpaceLarge))
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = color,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(SpaceMedium)
                .drawBehind {
                    val cornerRadiusPx = cornerRadius.toPx(
                        shapeSize = size,
                        density = Density(density)
                    )
                    val path = Path().apply {
                        moveTo(
                            size.width,
                            size.height - cornerRadiusPx
                        )
                        lineTo(size.width, size.height + triangleHeight.toPx())
                        lineTo(
                            size.width - triangleWidth.toPx(),
                            size.height - cornerRadiusPx
                        )
                        close()
                    }
                    drawPath(
                        path = path,
                        color = color
                    )
                }
        ) {
            Text(
                text = message,
                color = textColor
            )
        }

    }
}