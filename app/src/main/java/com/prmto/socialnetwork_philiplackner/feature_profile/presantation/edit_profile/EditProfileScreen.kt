package com.prmto.socialnetwork_philiplackner.feature_profile.presantation.edit_profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.prmto.socialnetwork_philiplackner.R
import com.prmto.socialnetwork_philiplackner.core.domain.states.StandardTextFieldState
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardTextField
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardToolbar
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceLarge
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceMedium
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SpaceSmall
import com.prmto.socialnetwork_philiplackner.feature_profile.util.EditProfileError
import kotlin.random.Random

@ExperimentalMaterialApi
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            navController = navController,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.edit_your_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            navActions = {
                IconButton(onClick = { }) {
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
                bannerImage = painterResource(id = R.drawable.channelart),
                profileImage = painterResource(id = R.drawable.avatar)
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
                        viewModel.setUsernameState(
                            state = StandardTextFieldState(text = it)
                        )
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
                        viewModel.setGithubTextFieldState(
                            state = StandardTextFieldState(text = it)
                        )
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
                        viewModel.setInstagramTextFieldState(
                            state = StandardTextFieldState(text = it)
                        )
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
                        viewModel.setLinkedinTextFieldState(
                            state = StandardTextFieldState(text = it)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.bioState.value.text,
                    error =  when (viewModel.bioState.value.error) {
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
                        viewModel.setBioState(
                            state = StandardTextFieldState(text = it)
                        )
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

                    var selected by remember {
                        mutableStateOf(false)
                    }

                    listOf(
                        "Kotlin",
                        "JavaScript",
                        "Assembly",
                        "C++",
                        "C#",
                        "Dart",
                        "Swift",
                        "TypeScript",
                        "Python"
                    ).forEach {

                        selected = Random.nextInt(2) == 0

                        FilterChip(
                            selected = selected,
                            onClick = { },
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
                                text = it,
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
    onProfileImageClick: () -> Unit = {}
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
                .height(bannerHeight),
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
                ),
            contentDescription = stringResource(id = R.string.profile_picture),
            contentScale = ContentScale.Crop
        )
    }
}