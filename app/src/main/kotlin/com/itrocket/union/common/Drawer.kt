package com.itrocket.union.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.BuildConfig
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.psb4
import com.itrocket.union.ui.white


@Composable
fun Drawer(
    fullName: String,
    onDestinationClicked: (type: DrawerScreenType) -> Unit,
    screens: List<DrawerScreens>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = fullName,
            style = AppTheme.typography.h6,
            color = white,
            modifier = Modifier
                .fillMaxWidth()
                .background(AppTheme.colors.mainColor)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.common_about_app),
                style = AppTheme.typography.h6,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.build_number, BuildConfig.VERSION_CODE),
                style = AppTheme.typography.body2,
                color = AppTheme.colors.secondaryColor
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        screens.forEachIndexed { index, screen ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onDestinationClicked(screen.type) },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple()
                    )
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(screen.icon), contentDescription = null)
                Spacer(modifier = Modifier.width(24.dp))
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = stringResource(screen.title),
                        style = AppTheme.typography.body2,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (index != screens.lastIndex) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(psb4)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

sealed class DrawerScreens(
    val title: Int,
    val icon: Int,
    val type: DrawerScreenType
) {
    object Settings : DrawerScreens(
        title = R.string.common_settings,
        icon = R.drawable.ic_drawer_settings,
        type = DrawerScreenType.SETTINGS
    )

    object Sync : DrawerScreens(
        title = R.string.sync,
        type = DrawerScreenType.SYNC,
        icon = R.drawable.ic_drawer_sync
    )

    object Logout : DrawerScreens(
        R.string.common_logout,
        type = DrawerScreenType.LOGOUT,
        icon = R.drawable.ic_logout
    )
}

enum class DrawerScreenType {
    SETTINGS, LOGOUT, SYNC
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
            "Оглоблина Анастасия Владимимровна",
            {},
            listOf(DrawerScreens.Settings, DrawerScreens.Sync, DrawerScreens.Logout)
        )
    }
}