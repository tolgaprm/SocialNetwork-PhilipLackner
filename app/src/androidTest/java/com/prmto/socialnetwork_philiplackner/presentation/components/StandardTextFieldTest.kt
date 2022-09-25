package com.prmto.socialnetwork_philiplackner.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prmto.socialnetwork_philiplackner.presentation.ui.theme.SocialNetworkPhilipLacknerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StandardTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Before
    fun setUp() {
        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }

            SocialNetworkPhilipLacknerTheme {
                StandardTextField(
                    text = text,
                    onValueChange = {
                        text = it
                    },
                    maxLength = 5,
                    modifier = Modifier.semantics {
                        testTag = ""
                    }
                )
            }
        }
    }

    @Test
    fun enterTooLongString_maxLengthNotExceeded() {
        composeTestRule.onNodeWithText("test")
            .performTextInput("123456")

        composeTestRule.onNodeWithText("test")
            .assertTextEquals("12345")

    }

}