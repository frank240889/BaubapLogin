package dev.franco.baubbap.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
)

@Composable
fun BaubapComposeTheme(
    orientation: Int = LocalConfiguration.current.orientation,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val orientationDimens = if (
        orientation == Configuration.ORIENTATION_LANDSCAPE
    ) {
        HorizontalOrientation
    } else {
        VerticalOrientation
    }

    val typographySize = TypographySize()

    CompositionLocalProvider(
        LocalOrientationDimens provides orientationDimens,
        LocalTypographySize provides typographySize,
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}

/**
 * Object to expose dynamically dimens.
 */
object BaubapComposeTheme {
    val typographySize: TypographySize
        @Composable
        get() = LocalTypographySize.current

    val orientation: Orientation
        @Composable
        get() = LocalOrientationDimens.current
}
