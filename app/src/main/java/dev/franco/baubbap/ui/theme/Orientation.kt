package dev.franco.baubbap.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This file expose the dynamic dimens depending on the device orientation.
 */
internal val DEFAULT_ICON_SIZE = 64.dp
internal val DEFAULT_PADDING = 40.dp
internal val DEFAULT_CORNER_RADIUS = 24.dp
internal val SMALL_CORNER_RADIUS = 16.dp
internal val BIG_CORNER_RADIUS = 32.dp
internal val DEFAULT_ELEVATION = 8.dp
internal val DEFAULT_SPACING = 24.dp
internal val SMALL_SPACING = 8.dp
internal const val DEFAULT_MAX_LENGTH_INPUT_PASSWORD = 30

val VerticalOrientation = Orientation()
val HorizontalOrientation = Orientation(
    defaultHorizontalPadding = 128.dp,
    defaultVerticalPadding = 16.dp,
    defaultPadding = 8.dp,
)

data class Orientation(
    val defaultIconSize: Dp = DEFAULT_ICON_SIZE,
    val defaultPadding: Dp = DEFAULT_PADDING,
    val defaultHorizontalPadding: Dp = DEFAULT_PADDING,
    val defaultVerticalPadding: Dp = DEFAULT_PADDING,
    val defaultCornerRadius: Dp = DEFAULT_CORNER_RADIUS,
    val smallCornerRadius: Dp = SMALL_CORNER_RADIUS,
    val bigCornerRadius: Dp = BIG_CORNER_RADIUS,
    val defaultElevation: Dp = DEFAULT_ELEVATION,
    val defaultVerticalSpacing: Dp = DEFAULT_SPACING,
    val smallSpacing: Dp = SMALL_SPACING,
)

val LocalOrientationDimens = staticCompositionLocalOf {
    Orientation()
}

/**
 * Object to expose dynamically dimens.
 */
object BaubapComposeTheme {
    val orientation: Orientation
        @Composable
        get() = LocalOrientationDimens.current
}
