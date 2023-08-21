package dev.franco.baubbap.ui.login

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Abc
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import dev.franco.baubap.R
import dev.franco.baubbap.ui.components.VerticalDivider
import dev.franco.baubbap.ui.theme.Background
import dev.franco.baubbap.ui.theme.Baubap
import dev.franco.baubbap.ui.theme.BaubapComposeTheme
import dev.franco.baubbap.ui.theme.Comfortaa
import dev.franco.baubbap.ui.theme.DEFAULT_MAX_LENGTH_INPUT_PASSWORD
import dev.franco.baubbap.ui.theme.Emphazis
import dev.franco.baubbap.ui.theme.Focused
import dev.franco.baubbap.ui.theme.Montserrat
import dev.franco.baubbap.ui.theme.Secondary2
import dev.franco.baubbap.ui.theme.Unfocused
import dev.franco.baubbap.validator.Curp
import dev.franco.baubbap.validator.InputType
import dev.franco.baubbap.validator.Nip
import dev.franco.baubbap.validator.Phone
import dev.franco.baubbap.validator.Result

private const val MAX_PHONE_LENGTH = 10
private const val MAX_CURP_LENGTH = 18

@Composable
internal fun LoginContent(
    modifier: Modifier = Modifier,
    showPassword: Boolean = false,
    onShowPassword: (Boolean) -> Unit,
    user: CharSequence,
    onUserChange: (CharSequence, InputType) -> Unit,
    password: CharSequence,
    onPasswordChange: (CharSequence) -> Unit,
    onLogin: () -> Unit,
    curpPhoneResult: Result,
    nipResult: Result,
    loading: Boolean,
) {
    val curpError = curpPhoneResult !is Curp.OnValid && curpPhoneResult !is Phone.OnValid &&
        curpPhoneResult !is Result.Idle

    val nipError = nipResult !is Nip.OnValid && nipResult !is Result.Idle

    val focusRequest = LocalFocusManager.current
    var currentInputType: InputType by remember {
        mutableStateOf(InputType.CURP)
    }

    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(
                    BaubapComposeTheme.orientation.defaultCornerRadius,
                ),
            )
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .align(Alignment.TopCenter),
        ) {
            Image(
                painter = painterResource(R.drawable.bg_baubap),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .align(Alignment.CenterEnd),
                contentScale = ContentScale.FillWidth,
                colorFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ColorFilter.tint(Color.Gray, BlendMode.Multiply)
                } else {
                    null
                },
            )

            Image(
                painter = painterResource(R.drawable.baubap_logo),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(
                        start = BaubapComposeTheme
                            .orientation
                            .smallSpacing
                            .times(2),
                        top = BaubapComposeTheme
                            .orientation
                            .defaultPadding,
                    )
                    .fillMaxWidth(0.3f),
            )

            Text(
                text = stringResource(R.string.welcome_back),
                fontFamily = Comfortaa,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = BaubapComposeTheme.typographySize.medium,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = BaubapComposeTheme.orientation.defaultVerticalSpacing.times(
                            2,
                        ),
                    ),
            )
        }

        Surface(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
                .align(Alignment.BottomCenter)
                .testTag("card_login_content")
                .layoutId("card_login_content"),
            color = Secondary2,
            shape = RoundedCornerShape(
                topStart = BaubapComposeTheme.orientation.defaultCornerRadius,
                topEnd = BaubapComposeTheme.orientation.defaultCornerRadius,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Spacer(
                    modifier = Modifier
                        .height(BaubapComposeTheme.orientation.smallSpacing),
                )

                Text(
                    text = stringResource(R.string.login_title),
                    fontFamily = Comfortaa,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray,
                    fontSize = BaubapComposeTheme.typographySize.medium,
                )

                Spacer(
                    modifier = Modifier
                        .height(BaubapComposeTheme.orientation.defaultVerticalSpacing),
                )

                OutlinedTextField(
                    textStyle = TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = BaubapComposeTheme.typographySize.small,
                    ),
                    value = user.toString(),
                    onValueChange = {
                        onUserChange.invoke(
                            it.take(
                                if (currentInputType == InputType.CURP) {
                                    MAX_CURP_LENGTH
                                } else {
                                    MAX_PHONE_LENGTH
                                },
                            )
                                .filter { value -> value.isLetterOrDigit() },
                            currentInputType,
                        )
                    },
                    leadingIcon = {
                        Row(
                            modifier = Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(
                                onClick = {
                                    currentInputType =
                                        if (currentInputType == InputType.PHONE_NUMBER) {
                                            InputType.CURP
                                        } else {
                                            InputType.PHONE_NUMBER
                                        }
                                    onUserChange.invoke("", currentInputType)
                                },
                            ) {
                                Icon(
                                    imageVector = if (currentInputType == InputType.PHONE_NUMBER) {
                                        Icons.Rounded.Abc
                                    } else {
                                        Icons.Rounded.PhoneAndroid
                                    },
                                    contentDescription = "",
                                )
                            }
                            VerticalDivider(
                                modifier = Modifier
                                    .height(
                                        BaubapComposeTheme
                                            .orientation
                                            .defaultVerticalSpacing,
                                    ),
                                color = Color.LightGray,
                            )
                        }
                    },
                    label = {
                        Text(
                            text = if (currentInputType == InputType.PHONE_NUMBER) {
                                stringResource(R.string.cellphone)
                            } else {
                                stringResource(R.string.curp)
                            },
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                    shape = RoundedCornerShape(
                        BaubapComposeTheme.orientation.defaultCornerRadius,
                    ),
                    isError = curpError,
                    keyboardActions = KeyboardActions {
                        this.defaultKeyboardAction(ImeAction.Next)
                    },
                    keyboardOptions = if (currentInputType == InputType.PHONE_NUMBER) {
                        KeyboardOptions(keyboardType = KeyboardType.Phone)
                    } else {
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Characters,
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Focused,
                        unfocusedBorderColor = Unfocused,
                        textColor = Color.DarkGray,
                        cursorColor = Color.DarkGray,
                        leadingIconColor = Color.Gray,
                        focusedLabelColor = Baubap,
                        unfocusedLabelColor = Color.Gray,
                    ),
                    enabled = !loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = BaubapComposeTheme.orientation.smallSpacing,
                        )
                        .testTag("login_user_input")
                        .layoutId("login_user_input"),
                )

                AnimatedVisibility(
                    visible = curpError,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(
                            horizontal = BaubapComposeTheme.orientation.smallSpacing,
                        )
                        .align(Alignment.CenterHorizontally)
                        .testTag("curp_phone_login_error")
                        .layoutId("curp_phone_login_error"),
                ) {
                    Text(
                        text = when (curpPhoneResult) {
                            is Phone.OnInvalid -> stringResource(R.string.invalid_phone)
                            is Curp.OnInvalid -> stringResource(R.string.invalid_curp)
                            is Curp.OnEmpty -> stringResource(R.string.empty_curp)
                            is Phone.OnEmpty -> stringResource(R.string.empty_phone)
                            else -> ""
                        },
                        fontFamily = Comfortaa,
                        fontWeight = FontWeight.Bold,
                        color = TextFieldDefaults
                            .outlinedTextFieldColors()
                            .cursorColor(curpError).value,
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally),
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(
                            BaubapComposeTheme.orientation.smallSpacing,
                        ),
                )

                OutlinedTextField(
                    textStyle = TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = BaubapComposeTheme.typographySize.small,
                    ),
                    value = password.toString(),
                    onValueChange = {
                        onPasswordChange.invoke(it.take(DEFAULT_MAX_LENGTH_INPUT_PASSWORD))
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.nip),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    visualTransformation = if (!showPassword) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    leadingIcon = {
                        Row(
                            modifier = Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(
                                onClick = {
                                    onShowPassword.invoke(showPassword.not())
                                },
                                modifier = Modifier,
                            ) {
                                Icon(
                                    imageVector = if (!showPassword) {
                                        Icons.Rounded.Visibility
                                    } else {
                                        Icons.Rounded.VisibilityOff
                                    },
                                    contentDescription = "",
                                )
                            }
                            VerticalDivider(
                                modifier = Modifier
                                    .height(
                                        BaubapComposeTheme
                                            .orientation
                                            .defaultVerticalSpacing,
                                    ),
                                color = Color.LightGray,
                            )
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    shape = RoundedCornerShape(
                        BaubapComposeTheme.orientation.defaultCornerRadius,
                    ),
                    isError = nipError,
                    keyboardActions = KeyboardActions {
                        this.defaultKeyboardAction(ImeAction.Done)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Focused,
                        unfocusedBorderColor = Unfocused,
                        textColor = Color.DarkGray,
                        cursorColor = Color.DarkGray,
                        leadingIconColor = Color.Gray,
                        focusedLabelColor = Baubap,
                        unfocusedLabelColor = Color.Gray,
                    ),
                    enabled = !loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = BaubapComposeTheme.orientation.smallSpacing,
                        )
                        .testTag("login_password_input")
                        .layoutId("login_password_input"),
                )

                AnimatedVisibility(
                    visible = nipError,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(
                            horizontal = BaubapComposeTheme.orientation.smallSpacing,
                        )
                        .align(Alignment.CenterHorizontally)
                        .testTag("nip_login_error")
                        .layoutId("nip_login_error"),
                ) {
                    Text(
                        text = when (nipResult) {
                            Nip.OnEmpty -> stringResource(R.string.empty_nip)
                            Nip.OnInvalid -> stringResource(R.string.invalid_nip)
                            else -> ""
                        },
                        fontFamily = Comfortaa,
                        fontWeight = FontWeight.Bold,
                        color = TextFieldDefaults
                            .outlinedTextFieldColors()
                            .cursorColor(nipError).value,
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally),
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(
                            BaubapComposeTheme.orientation.defaultVerticalSpacing,
                        ),
                )

                Button(
                    onClick = {
                        if (!loading && !curpError && !nipError) {
                            onLogin.invoke()
                        }
                        focusRequest.clearFocus()
                    },
                    shape = RoundedCornerShape(
                        BaubapComposeTheme.orientation.defaultCornerRadius,
                    ),
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Emphazis,
                        disabledBackgroundColor = Emphazis.copy(alpha = 0.7f),
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(BaubapComposeTheme.orientation.defaultPadding)
                        .padding(
                            horizontal = BaubapComposeTheme.orientation.smallSpacing,
                        )
                        .testTag("login_button")
                        .layoutId("login_button"),
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            color = Baubap,
                            modifier = Modifier
                                .size(BaubapComposeTheme.orientation.iconSize),
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Login,
                            contentDescription = "",
                            tint = Color.DarkGray,
                        )
                        Spacer(
                            modifier = Modifier
                                .width(
                                    BaubapComposeTheme
                                        .orientation
                                        .smallSpacing,
                                ),
                        )

                        Text(
                            text = stringResource(
                                R.string.login,
                            ).uppercase(),
                            fontSize = BaubapComposeTheme.typographySize.small,
                            fontFamily = Comfortaa,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                        )
                    }
                }

                TextButton(
                    onClick = {},
                    modifier = Modifier
                        .wrapContentWidth(),
                    shape = RoundedCornerShape(
                        BaubapComposeTheme.orientation.defaultCornerRadius,
                    ),
                    enabled = !loading,
                ) {
                    Text(
                        text = stringResource(R.string.forgot_nip),
                        color = Baubap,
                        fontFamily = Comfortaa,
                        fontWeight = FontWeight.Normal,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(
                            BaubapComposeTheme
                                .orientation
                                .defaultVerticalSpacing
                                .times(2),
                        ),
                )

                TextButton(
                    onClick = {},
                    shape = RoundedCornerShape(
                        BaubapComposeTheme.orientation.defaultCornerRadius,
                    ),
                    enabled = !loading,
                    modifier = Modifier
                        .padding(
                            bottom = BaubapComposeTheme
                                .orientation
                                .defaultVerticalPadding
                                .times(2),
                        ),
                ) {
                    Text(
                        text = stringResource(R.string.do_not_have_account),
                        color = Baubap,
                        fontFamily = Comfortaa,
                        fontWeight = FontWeight.Normal,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LoginContentPreview() {
    MaterialTheme {
        LoginContent(
            onShowPassword = {},
            user = "franco omar",
            onUserChange = { _, _ -> },
            password = "franco",
            onPasswordChange = {},
            onLogin = {},
            curpPhoneResult = Result.Idle,
            nipResult = Result.Idle,
            loading = false,
        )
    }
}
