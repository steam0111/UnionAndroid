package com.itrocket.union.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.BuildConfig


@Composable
fun Drawer(
    firstName: String,
    lastName: String,
    patronimic: String,
    onDestinationClicked: (type: DrawerScreenType) -> Unit,
    screens: List<DrawerScreens>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 48.dp, bottom = 56.dp, end = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(modifier = Modifier.weight(2f), verticalArrangement = Arrangement.Top) {
            if (lastName.isNotEmpty()) {
                Text(
                    text = lastName,
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.mainColor
                )
            }

            if (firstName.isNotEmpty()) {
                Text(
                    text = firstName,
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.mainColor
                )
            }

            if (patronimic.isNotEmpty()) {
                Text(
                    text = patronimic,
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.mainColor
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.build_number, BuildConfig.VERSION_CODE),
                style = AppTheme.typography.caption,
                color = AppTheme.colors.mainColor
            )
        }

        screens.forEach { screen ->
            if (screen.icon != null) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .clickable {
                            onDestinationClicked(screen.type)
                        }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = screen.title),
                            style = AppTheme.typography.body1,
                            color = AppTheme.colors.mainTextColor
                        )
                        Spacer(Modifier.width(4.dp))
                        Image(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = screen.icon),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(AppTheme.colors.mainTextColor)
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDestinationClicked(screen.type)
                    }
                    .weight(2f)) {
                    Text(
                        text = stringResource(id = screen.title),
                        style = AppTheme.typography.body1,
                        color = AppTheme.colors.mainTextColor
                    )
                }
            }
        }
    }
}

sealed class DrawerScreens(
    val title: Int,
    val icon: Int? = null,
    val type: DrawerScreenType
) {
    object Settings : DrawerScreens(
        R.string.label_settings, R.drawable.ic_settings,
        DrawerScreenType.SETTINGS
    )

    object Logout : DrawerScreens(R.string.common_exit, type = DrawerScreenType.LOGOUT)
}

enum class DrawerScreenType {
    SETTINGS, LOGOUT
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun DrawerPreview() {
    AppTheme {
        Drawer(
            "Анастасия",
            "Оглоблина",
            "Владимимровна",
            {},
            listOf(DrawerScreens.Settings, DrawerScreens.Logout)
        )
    }
}