package dev.franco.baubbap.ui.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import dev.franco.baubbap.ui.components.BaubapSnackbarHost
import dev.franco.baubbap.validator.InputType

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var showPassword by remember {
        mutableStateOf(false)
    }

    val loading = loginViewModel.loading

    val curpPhoneValidation = loginViewModel.curpValidation
    val nipValidation = loginViewModel.nipValidation

    val loginResult = loginViewModel.loginState

    LaunchedEffect(loginResult) {
        loginResult.message.run {
            if (this.isNotEmpty()) {
                snackbarHostState.showSnackbar(
                    message = this.toString(),
                    duration = SnackbarDuration.Short,
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LoginContent(
            user = loginViewModel.loginData.curpPhone.toString(),
            onUserChange = { value: CharSequence, inputType: InputType ->
                loginViewModel.setUser(value, inputType)
            },
            password = loginViewModel.loginData.nip.toString(),
            onPasswordChange = {
                loginViewModel.setPassword(it)
            },
            onLogin = {
                loginViewModel.login()
            },
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            showPassword = showPassword,
            onShowPassword = {
                showPassword = it
            },
            curpPhoneResult = curpPhoneValidation,
            nipResult = nipValidation,
            loading = loading,
        )
        BaubapSnackbarHost(
            snackbarHostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter),
        )
    }
}
