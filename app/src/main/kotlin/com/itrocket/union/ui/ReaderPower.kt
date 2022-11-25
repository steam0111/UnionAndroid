package com.itrocket.union.ui

import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.itrocket.utils.rememberViewInteropNestedScrollConnection
import com.itrocket.utils.toPx

@Composable
fun ReaderPowerPicker(
    power: Int,
    onPowerChanged: (String) -> Unit,
    onArrowDownClickListener: () -> Unit,
    onArrowUpClickListener: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(vertical = 24.dp, horizontal = 16.dp)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .background(white),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_arrow_down),
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
            modifier = Modifier.clickable(
                onClick = onArrowDownClickListener,
                indication = rememberRipple(bounded = false),
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
        )
        Spacer(modifier = Modifier.width(24.dp))
        val selectedTextSize = 20.toPx.toFloat()
        val textSize = 14.toPx.toFloat()
        val dividerSize = 1.toPx
        AndroidView(
            update = {
                it.value = power
            },
            factory = {
                com.itrocket.number_picker.NumberPicker(it).apply {
                    setOnValueChangedListener { _, _, current -> onPowerChanged(current.toString()) }
                    value = power
                    minValue = ReaderPowerInteractor.MIN_READER_POWER
                    maxValue = ReaderPowerInteractor.MAX_READER_POWER

                    setSelectedTextSize(selectedTextSize)
                    setTextSize(textSize)
                    setSelectedTypeface(Typeface.DEFAULT_BOLD)
                    setDividerColorResource(R.color.psb_4)
                    setDividerThickness(dividerSize)
                }
            },
            modifier = Modifier
                .weight(1f)
                .nestedScroll(rememberViewInteropNestedScrollConnection()),
        )
        Spacer(modifier = Modifier.width(24.dp))
        Image(
            painter = painterResource(R.drawable.ic_arrow_up),
            colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
            modifier = Modifier.clickable(
                onClick = onArrowUpClickListener,
                indication = rememberRipple(bounded = false),
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ),
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun ReaderPowerPickerPreview() {
    ReaderPowerPicker(
        power = 2,
        onPowerChanged = {},
        onArrowDownClickListener = {},
        onArrowUpClickListener = {}
    )
}