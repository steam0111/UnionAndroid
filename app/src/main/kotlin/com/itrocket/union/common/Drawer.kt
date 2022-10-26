package com.itrocket.union.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme

@Composable
fun Drawer(
    firstName: String,
    lastName: String,
    patronimic: String,
    onDestinationClicked: (type: DrawerScreenType) -> Unit,
    screens: List<DrawerScreens>
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
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

        screens.forEach { screen ->
            Spacer(Modifier.height(8.dp))
            if (screen.icon != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDestinationClicked(screen.type)
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = screen.title),
                        style = AppTheme.typography.body1,
                        color = AppTheme.colors.mainColor
                    )
                    Spacer(Modifier.width(4.dp))
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = screen.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(AppTheme.colors.mainColor)
                    )
                }
            } else {
                Text(
                    text = stringResource(id = screen.title),
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.mainColor,
                    modifier = Modifier.clickable {
                        onDestinationClicked(screen.type)
                    }
                )
            }
        }
    }
}

sealed class DrawerScreens(val title: Int, val icon: Int? = null, val type: DrawerScreenType) {
    object Account : DrawerScreens(
        R.string.label_settings, R.drawable.ic_settings,
        DrawerScreenType.SETTINGS
    )

    object Logout : DrawerScreens(R.string.common_exit, type = DrawerScreenType.LOGOUT)
}

enum class DrawerScreenType {
    SETTINGS, LOGOUT
}

@Preview
@Composable
fun DrawerPreview() {
    AppTheme {
        Drawer("Анастасия", "Оглоблина", "Владимимровна", {}, listOf())
    }
}