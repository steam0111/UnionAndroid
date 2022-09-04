package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

private const val FIRST_INDEX = 0

@Composable
fun ReadingModeTabs(
    tabs: List<ReadingModeTab>,
    selectedTab: ReadingModeTab,
    onClick: (ReadingModeTab) -> Unit
) {
    Row(Modifier.fillMaxWidth()) {
        tabs.forEachIndexed { index, readingModeTab ->
            val isFirst = index == FIRST_INDEX
            val isLast = index == tabs.lastIndex
            ReadingModeTab(
                tab = readingModeTab,
                isFirst = isFirst,
                isLast = isLast,
                isSelected = readingModeTab == selectedTab,
                onClick = onClick
            )
            if (!isLast) {
                Spacer(modifier = Modifier.width(1.dp))
            }
        }
    }
}

@Composable
fun RowScope.ReadingModeTab(
    tab: ReadingModeTab,
    isFirst: Boolean,
    isLast: Boolean,
    isSelected: Boolean,
    onClick: (ReadingModeTab) -> Unit
) {
    val backgroundColor = if (isSelected) {
        AppTheme.colors.appBarBackgroundColor
    } else {
        psb4
    }
    val rectangle = when {
        isFirst -> RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
        isLast -> RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
        else -> MaterialTheme.shapes.large
    }
    Text(
        text = stringResource(tab.textId).uppercase(),
        style = AppTheme.typography.body2,
        fontWeight = FontWeight.Medium,
        color = white,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .weight(1f)
            .background(backgroundColor, rectangle)
            .padding(vertical = 12.dp)
            .clickable(onClick = {
                onClick(tab)
            })

    )
}

@Preview
@Composable
fun ReadingModeTabsPreview() {
    ReadingModeTabs(
        tabs = ReadingModeTab.values().toList(),
        selectedTab = ReadingModeTab.RFID,
        onClick = {}
    )
}