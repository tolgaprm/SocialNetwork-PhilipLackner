package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.edit_profile

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardTextField
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceSmall
import com.prmto.socialnetwork_philiplackner.core.presentation.util.CropActivityResultContract
import com.prmto.socialnetwork_philiplackner.core.presentation.util.RequestPermissionUtil
import com.prmto.socialnetwork_philiplackner.core.presentation.util.UiEvent
import com.prmto.socialnetwork_philiplackner.core.presentation.util.asString
import com.prmto.socialnetwork_philiplackner.feature_profile.util.EditProfileError
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun EditProfileScreen(
    scaffoldState: ScaffoldState,
    viewModel: EditProfileViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {}
) {

    val profileState = viewModel.profileState.value
    val context = LocalContext.current
    val activity = context as Activity
    val coroutineScope = rememberCoroutineScope()

    val cropProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(
            aspectRatioX = 1f,
            aspectRatioY = 1f
        )
    ) {
        viewModel.onEvent(EditProfileEvents.CropProfileImage(it))
    }

    val cropBannerImageLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(
            aspectRatioX = 5f,
            aspectRatioY = 2f
        )
    ) {
        viewModel.onEvent(EditProfileEvents.CropBannerImage(it))
    }

    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let {
            cropProfilePictureLauncher.launch(it)
        }
    }

    val bannerImageGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let {
            cropBannerImageLauncher.launch(it)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            when (viewModel.clickedImage.value) {
                EditProfileViewModel.ClickedImage.Profile -> {
                    profilePictureGalleryLauncher.launch(PickVisualMediaRequest())
                }
                EditProfileViewModel.ClickedImage.BannerImage -> {
                    bannerImageGalleryLauncher.launch(PickVisualMediaRequest())
                }
            }
        }
    }


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.edit_your_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            onNavigateUp = onNavigateUp,
            navActions = {
                IconButton(onClick = {
                    viewModel.onEvent(EditProfileEvents.UpdateProfile)
                }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.save_changes),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            BannerEditSection(
                bannerImage = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = viewModel.bannerUri.value ?: profileState.profile?.bannerUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                profileImage = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(
                            data = viewModel.profilePictureUri.value
                                ?: profileState.profile?.profilePictureUrl
                        )
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                onBannerClick = {
                    val permission = RequestPermissionUtil.getMediaAccessPermissionBySDK()
                    RequestPermissionUtil.request(
                        permission = permission,
                        context = context,
                        activity = activity,
                        onShowSnackbarRationale = {
                            val text = context.getString(R.string.necessary_to_pick_image)
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    text
                                )
                            }
                        },
                        onPermissionLauncher = {
                            permissionLauncher.launch(permission)
                        },
                        onGalleryLauncher = {
                            bannerImageGalleryLauncher.launch(
                                PickVisualMediaRequest()
                            )
                        }
                    )
                },
                onProfilePictureClick = {
                    val permission = RequestPermissionUtil.getMediaAccessPermissionBySDK()
                    RequestPermissionUtil.request(
                        permission = permission,
                        context = context,
                        activity = activity,
                        onShowSnackbarRationale = {
                            val text = context.getString(R.string.necessary_to_pick_image)
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    text
                                )
                            }
                        },
                        onPermissionLauncher = {
                            permissionLauncher.launch(permission)
                        },
                        onGalleryLauncher = {
                            profilePictureGalleryLauncher.launch(
                                PickVisualMediaRequest()
                            )
                        }
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge)
            ) {
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.userNameState.value.text,
                    error = when (viewModel.userNameState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.error_field_empty
                        )
                        else -> ""
                    },
                    leadingIcon = Icons.Default.Person,
                    hint = stringResource(id = R.string.username_hint),
                    onValueChange = {
                        viewModel.onEvent(EditProfileEvents.EnteredUserName(it))
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.githubTextFieldState.value.text,
                    error = when (viewModel.githubTextFieldState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.error_field_empty
                        )
                        else -> ""
                    },
                    hint = stringResource(id = R.string.github_profile_url),
                    leadingIcon = ImageVector.vectorResource(R.drawable.ic_github),
                    onValueChange = {
                        viewModel.onEvent(EditProfileEvents.EnteredGitHubUrl(it))
                    }
                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.instagramTextFieldState.value.text,
                    error = when (viewModel.instagramTextFieldState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.error_field_empty
                        )
                        else -> ""
                    },
                    hint = stringResource(id = R.string.instagram_profile_url),
                    leadingIcon = ImageVector.vectorResource(R.drawable.ic_instagram),
                    onValueChange = {
                        viewModel.onEvent(EditProfileEvents.EnteredInstagramUrl(it))
                    }
                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.linkedinTextFieldState.value.text,
                    error = when (viewModel.linkedinTextFieldState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.error_field_empty
                        )
                        else -> ""
                    },
                    hint = stringResource(id = R.string.linkedin_profile_url),
                    leadingIcon = ImageVector.vectorResource(R.drawable.ic_linkedin),
                    onValueChange = {
                        viewModel.onEvent(EditProfileEvents.EnteredLinkedInUrl(it))
                    }
                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.bioState.value.text,
                    error = when (viewModel.bioState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.error_field_empty
                        )
                        else -> ""
                    },
                    hint = stringResource(id = R.string.your_bio),
                    singleLine = false,
                    maxLength = Int.MAX_VALUE,
                    maxlines = 3,
                    leadingIcon = Icons.Default.Description,
                    onValueChange = {
                        viewModel.onEvent(EditProfileEvents.EnteredBio(it))
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Text(
                    text = stringResource(id = R.string.select_top_3_skills),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(SpaceMedium))


                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisAlignment = MainAxisAlignment.Center,
                    mainAxisSpacing = SpaceMedium,
                    crossAxisSpacing = SpaceSmall
                ) {
                    viewModel.skills.value.skills.forEach { skill ->
                        val selected = skill in viewModel.skills.value.selectedSkills
                        FilterChip(
                            selected = selected,
                            onClick = { viewModel.onEvent(EditProfileEvents.SetSkillSelected(skill)) },
                            colors = ChipDefaults.filterChipColors(
                                backgroundColor = MaterialTheme.colors.background,
                                selectedContentColor = MaterialTheme.colors.primary,
                                disabledContentColor = MaterialTheme.colors.onSurface
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                            ),
                        ) {
                            Text(
                                text = skill.name,
                                modifier = Modifier.padding(SpaceSmall),
                                color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BannerEditSection(
    bannerImage: Painter,
    profileImage: Painter,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    onBannerClick: () -> Unit = {},
    onProfilePictureClick: () -> Unit = {}
) {
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight + profilePictureSize / 2f)
    ) {
        Image(
            painter = bannerImage,
            contentDescription = stringResource(id = R.string.banner_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(bannerHeight)
                .clickable { onBannerClick() },
            contentScale = ContentScale.Crop
        )

        Image(
            painter = profileImage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(profilePictureSize)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = CircleShape
                )
                .clickable { onProfilePictureClick() },
            contentDescription = stringResource(id = R.string.profile_picture),
            contentScale = ContentScale.Crop
        )
    }
}