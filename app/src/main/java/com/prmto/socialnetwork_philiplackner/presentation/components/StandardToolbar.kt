package com.prmto.socialnetwork_philiplackner.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prmto.socialnetwork_philiplackner.R

@Composable
fun StandardToolbar(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBackArrow: Boolean = false,
    title: @Composable () -> Unit = {},
    navActions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        },
        actions = navActions,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp
    )
}