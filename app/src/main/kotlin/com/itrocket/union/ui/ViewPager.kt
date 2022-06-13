package com.itrocket.union.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.ui.BaseTab
import com.itrocket.ui.TabIndicator

@ExperimentalPagerApi
@Composable
fun TabIndicatorBlack(tabPositions: List<TabPosition>, pagerState: PagerState) {
    TabIndicator(tabPositions = tabPositions, pagerState = pagerState, backgroundColor = psb1)
}

@ExperimentalPagerApi
@Composable
fun DoubleTabRow(
    modifier: Modifier,
    selectedPage: Int,
    targetPage: Int,
    tabs: List<BaseTab>,
    onTabClickListener: (Int) -> Unit,
    tabIndicator: @Composable (List<TabPosition>) -> Unit,
    backgroundColor: Color = white,
    selectedTextColor: Color = white,
    unselectedTextColor: Color = graphite6,
    enabled: Boolean = true
) {
    TabRow(
        modifier = modifier,
        divider = { },
        indicator = {
            tabIndicator(it)
        },
        selectedTabIndex = selectedPage,
        backgroundColor = backgroundColor,
    ) {
        tabs.forEachIndexed { index, item ->
            Text(
                text = item.title,
                style = AppTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = if (targetPage == index) {
                    selectedTextColor
                } else {
                    unselectedTextColor
                },
                modifier = Modifier
                    .padding(6.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable(enabled = enabled) {
                        onTabClickListener(index)
                    }
                    .padding(vertical = 9.dp)
                    .zIndex(2f)
            )
        }
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun DoubleTabRowPreview() {
    val pagerState = rememberPagerState(1)
    val tabs = listOf(
        BaseTab(
            title = "1",
            screen = {

            }
        ),
        BaseTab(
            title = "2",
            screen = {

            }
        )
    )
    DoubleTabRow(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                color = graphite2,
                shape = RoundedCornerShape(8.dp)
            ),
        targetPage = 1,
        selectedPage = 1,
        tabs = tabs,
        onTabClickListener = {

        },
        tabIndicator = {
            TabIndicatorBlack(it, pagerState)
        })

    HorizontalPager(count = tabs.size, state = pagerState) { page ->
        tabs[page].screen()
    }
}