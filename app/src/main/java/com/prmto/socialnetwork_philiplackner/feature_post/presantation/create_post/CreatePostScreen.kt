package com.prmto.socialnetwork_philiplackner.feature_post.presantation.create_post

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardTextField
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceSmall
import com.prmto.socialnetwork_philiplackner.core.presentation.util.CropActivityResultContract
import com.prmto.socialnetwork_philiplackner.feature_post.util.PostDescriptionError

@ExperimentalCoilApi
@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: CreatePostViewModel = hiltViewModel()
) {

    val imageUri = viewModel.chosenImageUri.value

    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract()
    ) {
        viewModel.onEvent(CreatePostEvent.CropImage(it))
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        cropActivityLauncher.launch(it)
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        StandardToolbar(
            navController = navController,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.create_a_new_post),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            navActions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.create_post),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceLarge),
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.choose_image),
                    tint = MaterialTheme.colors.onBackground
                )
                imageUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(
                            request = ImageRequest.Builder(LocalContext.current)
                                .data(uri)
                                .build()
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.post_image),
                        modifier = Modifier.matchParentSize(),
                    )
                }
            }

            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.descriptionState.value.text,
                error = when (viewModel.descriptionState.value.error) {
                    PostDescriptionError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                hint = stringResource(id = R.string.description),
                singleLine = false,
                maxLength = Int.MAX_VALUE,
                maxlines = 5,
                onValueChange = {
                    viewModel.onEvent(CreatePostEvent.EnteredDescription(it))
                }
            )

            Spacer(modifier = Modifier.height(SpaceLarge))

            Button(
                onClick = {
                    viewModel.onEvent(CreatePostEvent.PostImage)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.post),
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
        }
    }
}