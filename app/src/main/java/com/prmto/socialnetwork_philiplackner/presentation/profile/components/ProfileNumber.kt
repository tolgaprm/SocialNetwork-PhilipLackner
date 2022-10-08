package com.prmto.socialnetwork_philiplackner.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SpaceSmall

@Composable
fun ProfileNumber(
    modifier: Modifier = Modifier,
    number: Int,
    text: String
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.h1.copy(
                fontSize = 24.sp
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(SpaceSmall))
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}