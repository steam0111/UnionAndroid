package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R

@Composable
fun FullCharacteristicsSwitcher(
    isChecked: Boolean,
    onCheckedChangeListener: (Boolean) -> Unit
) {
    Box(
        Modifier
            .background(color = psb4)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = white, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.accounting_object_detail_show_full_characteristics),
                style = AppTheme.typography.subtitle2,
                color = AppTheme.colors.mainTextColor
            )
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChangeListener,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = white,
                    checkedTrackColor = AppTheme.colors.mainColor
                )
            )
        }
    }
}

@Preview
@Composable
fun FullCharacteristicsSwitcherPreview() {
    FullCharacteristicsSwitcher(false, {})
}