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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prmto.socialnetwork_philiplackner.core.presentation.components.StandardTextField
import com.prmto.socialnetwork_philiplackner.core.presentation.ui.theme.SocialNetworkPhilipLacknerTheme
import com.prmto.socialnetwork_philiplackner.core.util.TestTags.STANDARD_TEXT_FIELD
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StandardTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun enterTooLongString_maxLengthNotExceeded() {
        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }

            SocialNetworkPhilipLacknerTheme {
                StandardTextField(
                    text = text,
                    onValueChange = {
                        text = it
                    },
                    maxLength = 5
                )
            }
        }
        val expectedString = "aaaaa"

        composeTestRule.onNodeWithTag(STANDARD_TEXT_FIELD)
            .performTextClearance()

        composeTestRule.onNodeWithTag(STANDARD_TEXT_FIELD)
            .performTextInput(expectedString)

        composeTestRule.onNodeWithTag(STANDARD_TEXT_FIELD)
            .performTextInput("a")

        composeTestRule.onNodeWithTag(STANDARD_TEXT_FIELD)
            .assertTextEquals(expectedString)

    }

    @Test
    fun enterPassword_toggleVisibility_passwordVisible() {
        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }

            SocialNetworkPhilipLacknerTheme {
                StandardTextField(
                    text = text,
                    onValueChange = {
                        text = it
                    },
                    keyboardType = KeyboardType.Password,
                    maxLength = 5,
                    modifier = Modifier.semantics {
                        testTag = STANDARD_TEXT_FIELD
                    }
                )
            }

            composeTestRule.onNodeWithTag(STANDARD_TEXT_FIELD)
                .performTextInput("aaaaa")


        }
    }

}