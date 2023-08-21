package dev.franco.baubbap.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.franco.baubap.R

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
)

val Montserrat = FontFamily(
    listOf(
        Font(R.font.montserrat_bold, FontWeight.Bold),
        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat_thin, FontWeight.Thin),
    ),
)

val Comfortaa = FontFamily(
    listOf(
        Font(R.font.comfortaa_bold, FontWeight.Bold),
        Font(R.font.comfortaa_medium, FontWeight.Medium),
        Font(R.font.comfortaa_regular, FontWeight.Normal),
        Font(R.font.comfortaa_light, FontWeight.Light),
    ),
)
