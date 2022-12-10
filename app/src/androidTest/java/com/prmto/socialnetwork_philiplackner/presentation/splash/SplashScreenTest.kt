package com.prmto.socialnetwork_philiplackner.presentation.splash

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SocialNetworkPhilipLacknerTheme
import com.prmto.socialnetwork_philiplackner.feature_auth.presantation.splash.SplashScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    lateinit var navController: TestNavHostController


    @Test
    fun splashScreen_displaysAndDisappears() = runTest {


        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            SocialNetworkPhilipLacknerTheme {
                SplashScreen(
                    navController = navController
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Logo")
            .assertExists()

    }
}