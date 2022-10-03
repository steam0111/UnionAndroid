package com.itrocket.union.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itrocket.union.theme.domain.entity.ColorSettings
import org.koin.androidx.compose.get

class Dimensions(
    val auth_container_horizontal: Dp
)

private val normalDimensions = Dimensions(
    auth_container_horizontal = 48.dp
)

private val smallDimensions = Dimensions(
    auth_container_horizontal = 24.dp
)

private val defaultColors = ColorSettings()

private val defaultTypography: Typography = Typography()

@Composable
fun ProvideTypography(
    typography: Typography,
    content: @Composable () -> Unit
) {
    val typographies = remember {
        typography
    }
    CompositionLocalProvider(LocalAppTypography provides typographies, content = content)
}

private val LocalAppTypography = staticCompositionLocalOf {
    defaultTypography
}

@Composable
fun ProvideColors(
    colorSettings: ColorSettings,
    content: @Composable () -> Unit
) {
    val colors = remember {
        colorSettings
    }
    CompositionLocalProvider(LocalAppColors provides colors, content = content)
}

private val LocalAppColors = staticCompositionLocalOf {
    defaultColors
}

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
    val isPreview = LocalInspectionMode.current
    val colors: ColorSettings = if (isPreview) {
        ColorSettings()
    } else {
        get()
    }
    val paletteColor = if (darkTheme) {
        darkColors(primary = colors.mainColor)
    } else {
        lightColors(primary = colors.mainColor)
    }

    val configuration = LocalConfiguration.current
    val dimensions = if (configuration.screenWidthDp <= 320) smallDimensions else normalDimensions

    ProvideColors(colorSettings = colors) {
        ProvideTypography(typography = defaultTypography.changeTextColor(colors.mainTextColor)) {
            ProvideDimens(dimensions = dimensions) {
                CompositionLocalProvider {
                    MaterialTheme(
                        colors = paletteColor,
                        content = content
                    )
                }
            }
        }
    }
}

object AppTheme {
    val colors: ColorSettings
        @Composable
        get() = LocalAppColors.current

    val typography: Typography
        @Composable
        get() = LocalAppTypography.current

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current
}