package dev.franco.baubbap.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.franco.baubbap.ui.theme.Baubap
import dev.franco.baubbap.ui.theme.BaubapComposeTheme
import dev.franco.baubbap.ui.theme.Comfortaa
import dev.franco.baubbap.ui.theme.Focused

@Composable
fun BoxScope.BaubapSnackbarHost(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    SnackbarHost(
        modifier = modifier.align(Alignment.BottomCenter),
        hostState = snackbarHostState,
        snackbar = { snackbarData: SnackbarData ->
            Card(
                border = BorderStroke(1.dp, Focused),
                shape = RoundedCornerShape(BaubapComposeTheme.orientation.smallCornerRadius),
                backgroundColor = Baubap,
                modifier = Modifier
                    .padding(BaubapComposeTheme.orientation.defaultHorizontalPadding)
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.padding(BaubapComposeTheme.orientation.smallSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "",
                        tint = Color.White,
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = BaubapComposeTheme.orientation.smallSpacing),
                    )
                    Text(
                        text = snackbarData.message,
                        color = Color.White,
                        fontFamily = Comfortaa,
                    )
                }
            }
        },
    )
}
