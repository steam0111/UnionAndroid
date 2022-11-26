package com.itrocket.union.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.utils.clickableUnbounded

@Composable
fun ExpandedInfoField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(white)
                .padding(start = 24.dp, top = 12.dp, end = 24.dp)

        ) {
            val fraction = getFraction(value)
            Text(
                text = label,
                style = AppTheme.typography.body1,
                modifier = Modifier.fillMaxWidth(fraction)
            )
            Spacer(modifier = Modifier.width(32.dp))
            ExpandedInfoValue(value)
        }
        Spacer(modifier = Modifier.height(12.dp))
        BottomLine()
    }
}

private fun getFraction(value: String) = when {
    value.toBooleanStrictOrNull() != null -> 0.8f
    else -> 0.5f
}

@Composable
private fun RowScope.ExpandedInfoValue(value: String) {
    when {
        value.toBooleanStrictOrNull() != null -> {
            Spacer(modifier = Modifier.weight(1f))
            BaseCheckbox(
                isChecked = value.toBooleanStrict(),
                onCheckClickListener = {},
                enabled = false
            )
        }
        else -> {
            Text(
                text = value,
                style = AppTheme.typography.body1,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SelectedBaseField(
    isShowBottomLine: Boolean = true,
    label: String,
    value: String,
    onFieldClickListener: () -> Unit,
    clickable: Boolean = true,
    underlineColor: Color = brightGray,
    isCrossVisible: Boolean = false,
    onCrossClickListener: () -> Unit = {}
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(enabled = clickable) {
                    onFieldClickListener()
                }
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = label, style = AppTheme.typography.caption, color = graphite5)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = value, style = AppTheme.typography.body2)
            }
            if (isCrossVisible) {
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_cross),
                    contentDescription = null,
                    modifier = Modifier
                        .size(10.dp)
                        .clickableUnbounded(onClick = onCrossClickListener),
                    colorFilter = ColorFilter.tint(graphite4),
                )
            }
        }
        if (isShowBottomLine) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 16.dp)
                    .background(underlineColor)
            )
        }
    }
}

@Composable
fun UnselectedBaseField(
    isShowBottomLine: Boolean = true,
    label: String,
    onFieldClickListener: () -> Unit,
    underlineColor: Color = brightGray,
    clickable: Boolean = true,
    error: String = ""
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = clickable) {
                onFieldClickListener()
            }
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Text(text = label, style = AppTheme.typography.body2, color = graphite5)
        Spacer(modifier = Modifier.height(16.dp))
        if (isShowBottomLine) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(underlineColor)
            )
        }
        if (error.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = error, style = AppTheme.typography.caption, color = blue6)
        }
    }
}


@Composable
fun CheckBoxField(
    isShowBottomLine: Boolean = true,
    label: String,
    onFieldClickListener: () -> Unit,
    isSelected: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onFieldClickListener()
            }
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BaseCheckbox(
                isChecked = isSelected,
                onCheckClickListener = onFieldClickListener
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = label, style = AppTheme.typography.body2, color = graphite5)
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (isShowBottomLine) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(brightGray)
            )
        }
    }
}


@Composable
fun RadioButtonField(
    isShowBottomLine: Boolean = true,
    label: String,
    onFieldClickListener: () -> Unit,
    isSelected: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onFieldClickListener()
            }
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.mainTextColor,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 40.dp)
            )
            BaseRadioButton(isSelected = isSelected, onClick = onFieldClickListener)
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (isShowBottomLine) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(brightGray)
            )
        }
    }
}