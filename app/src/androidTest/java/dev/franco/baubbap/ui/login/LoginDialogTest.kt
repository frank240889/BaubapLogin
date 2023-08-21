package dev.franco.appcompose.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import dev.franco.baubbap.ui.login.LoginDialog
import org.junit.Rule
import org.junit.Test

class LoginDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenCompositionStartsThenDialogIsNotShown() {
        val dialogIsVisible by mutableStateOf(false)
        composeTestRule.setContent {
            if (dialogIsVisible) {
                LoginDialog(
                    title = "",
                    message = "",
                    onDismissRequest = {},
                    onConfirmButton = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag("login_dialog")
            .assertDoesNotExist()
    }

    @Test
    fun whenStateIsSuccessOrErrorThenDialogIsShown() {
        val dialogIsVisible by mutableStateOf(true)
        composeTestRule.setContent {
            if (dialogIsVisible) {
                LoginDialog(
                    title = "",
                    message = "",
                    onDismissRequest = {},
                    onConfirmButton = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag("login_dialog")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun titleAndMessageAreShownInDialog() {
        val expectedTitle = "Title"
        val expectedMessage = "Message"
        composeTestRule.setContent {
            LoginDialog(
                title = expectedTitle,
                message = expectedMessage,
                onDismissRequest = {},
                onConfirmButton = {},
            )
        }

        with(composeTestRule) {
            onNodeWithTag("login_dialog_title")
                .assertTextContains(expectedTitle)

            onNodeWithTag("login_dialog_text")
                .assertTextContains(expectedMessage)
        }
    }

    @Test
    fun whenConfirmButtonIsPressedThenLambdaIsInvoked() {
        val title = "Title"
        val message = "Message"
        var isConfirmButtonPressed = false
        val onConfirmButton: () -> Unit = {
            isConfirmButtonPressed = true
        }
        composeTestRule.setContent {
            LoginDialog(
                title = title,
                message = message,
                onDismissRequest = {},
                onConfirmButton = onConfirmButton,
            )
        }

        with(composeTestRule) {
            onNodeWithTag("login_dialog_confirm_button")
                .assertIsDisplayed()
                .assertExists()
                .performClick()
        }

        assert(isConfirmButtonPressed)
    }

    @Test
    fun whenDialogIsDismissedThenDialogIsNotShownAnymore() {
        var dialogIsVisible by mutableStateOf(true)
        val onConfirmButton: () -> Unit = {
            dialogIsVisible = false
        }
        composeTestRule.setContent {
            if (dialogIsVisible) {
                LoginDialog(
                    title = "",
                    message = "",
                    onDismissRequest = {},
                    onConfirmButton = onConfirmButton,
                )
            }
        }

        with(composeTestRule) {
            onNodeWithTag("login_dialog")
                .assertExists()
                .assertIsDisplayed()

            onNodeWithTag("login_dialog_confirm_button")
                .assertIsDisplayed()
                .assertExists()
                .performClick()

            onNodeWithTag("login_dialog")
                .assertDoesNotExist()
        }
    }
}
