package com.itrocket.union.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = psb1,
)

private val LightColorPalette = lightColors(
    primary = psb1,
)

class Dimensions(
    val auth_container_horizontal: Dp
)

private val normalDimensions = Dimensions(
    auth_container_horizontal = 48.dp
)

private val smallDimensions = Dimensions(
    auth_container_horizontal = 24.dp
)

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

private val LocalAppDimens = staticCompositionLocalOf {
    normalDimensions
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val configuration = LocalConfiguration.current
    val dimensions = if (configuration.screenWidthDp <= 320) smallDimensions else normalDimensions

    ProvideDimens(dimensions = dimensions) {
        CompositionLocalProvider() {
            MaterialTheme(
                colors = colors,
                content = content
            )
        }
    }
}

object AppTheme {
    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current
}