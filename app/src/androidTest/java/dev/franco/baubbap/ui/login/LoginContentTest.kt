package dev.franco.appcompose.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dev.franco.baubbap.validator.InputType
import dev.franco.baubbap.validator.Result
import dev.franco.baubbap.ui.login.LoginContent
import org.junit.Rule
import org.junit.Test

class LoginContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenComponentEntersToCompositionThenElementsAreVisibleAndEnabled() {
        composeTestRule.setContent {
            LoginContent(
                onShowPassword = {},
                user = "",
                onUserChange = { value, inputType -> },
                password = "",
                onPasswordChange = {},
                onLogin = {},
                curpPhoneResult = Result.Idle,
                nipResult = Result.Idle,
                loading = false,
            )
        }

        with(composeTestRule) {
            onNodeWithTag("login_user_input")
                .assertExists()
                .assertIsDisplayed()

            onNodeWithTag("login_password_input")
                .assertExists()
                .assertIsDisplayed()

            onNodeWithTag("login_button")
                .assertExists()
                .assertIsDisplayed()
                .assertIsEnabled()
        }
    }

    @Test
    fun whenUserTypesUserAndPasswordThenFieldsChange() {
        val expectedUser = "Franco"
        val expectedPassword = "Omar"
        var user by mutableStateOf("")
        var password by mutableStateOf("")

        val onUserChange: (CharSequence, InputType) -> Unit = { value, inputType -> }

        val onPasswordChange: (CharSequence) -> Unit = {
            password = it.toString()
        }

        composeTestRule.setContent {
            LoginContent(
                onShowPassword = {},
                user = "",
                onUserChange = onUserChange,
                password = "",
                onPasswordChange = onPasswordChange,
                onLogin = {},
                curpPhoneResult = Result.Idle,
                nipResult = Result.Idle,
                loading = false,
            )
        }

        with(composeTestRule) {
            onNodeWithTag("login_user_input")
                .performTextInput(expectedUser)

            onNodeWithTag("login_password_input")
                .performTextInput(expectedPassword)

            onNodeWithTag("login_button")
                .performClick()
        }

        assert(user == expectedUser)
        assert(password == expectedPassword)
    }

    @Test
    fun whenUserPressLoginButtonThenLambdaIsCalled() {
        var isLoginPressed by mutableStateOf(false)

        val onLogin: () -> Unit = {
            isLoginPressed = true
        }

        composeTestRule.setContent {
            LoginContent(
                onShowPassword = {},
                user = "",
                onUserChange = { value, inputType -> },
                password = "",
                onPasswordChange = {},
                onLogin = {},
                curpPhoneResult = Result.Idle,
                nipResult = Result.Idle,
                loading = false,
            )
        }

        composeTestRule
            .onNodeWithTag("login_button")
            .performClick()

        assert(isLoginPressed)
    }
}
