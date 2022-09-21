package com.itrocket.union.ui

import android.widget.NumberPicker
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.itrocket.union.R
import com.itrocket.union.readerPower.domain.ReaderPowerInteractor
import com.itrocket.utils.clickableUnbounded
import com.itrocket.utils.rememberViewInteropNestedScrollConnection

private const val READER_POWER_DOT_COUNT = 5

@Composable
fun ReaderPowerSelector(
    power: Int,
    onClickListener: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(AppTheme.colors.mainColor, RoundedCornerShape(8.dp))
            .padding(start = 4.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_lightning),
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
            modifier = Modifier
                .background(white, RoundedCornerShape(8.dp))
                .padding(vertical = 7.dp, horizontal = 14.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        for (index in 1..READER_POWER_DOT_COUNT) {
            val baseModifier = Modifier
                .size(16.dp)
                .background(white, CircleShape)
                .clickableUnbounded {
                    onClickListener(index)
                }
            if (index > power) {
                Box(
                    modifier = baseModifier
                        .padding(2.dp)
                        .background(AppTheme.colors.mainColor, CircleShape)

                )
            } else {
                Box(
                    modifier = baseModifier
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
@Preview
fun ReaderPowerSelectorPreview() {
    ReaderPowerSelector(power = 3, onClickListener = {})
}