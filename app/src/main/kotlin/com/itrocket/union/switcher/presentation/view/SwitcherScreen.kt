package com.itrocket.union.switcher.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.core.base.AppInsets
import com.itrocket.union.switcher.presentation.store.SwitcherStore
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.ui.BaseTab
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import com.itrocket.union.ui.DoubleTabRow
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.TabRowIndicator
import com.itrocket.union.ui.TextButton
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwitcherScreen(
    state: SwitcherStore.State,
    appInsets: AppInsets,
    onCrossClickListener: () -> Unit,
    onCancelClickListener: () -> Unit,
    onContinueClickListener: () -> Unit,
    onTabClickListener: (Int) -> Unit,
) {

    val pagerState = rememberPagerState(state.selectedPage)
    val tabs = state.switcherData.values.map {
        BaseTab(
            title = it.text ?: it.textId?.let { stringResource(it) }.orEmpty(),
            screen = {

            }
        )
    }
    val coroutineScope = rememberCoroutineScope()

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = appInsets.bottomInset.dp, top = appInsets.topInset.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(white, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(top = 16.dp),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(state.switcherData.titleId),
                        style = AppTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = null,
                        modifier = Modifier.clickableUnbounded(onClick = onCrossClickListener)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                MediumSpacer()
                DoubleTabRow(
                    modifier = Modifier
                        .padding(16.dp)
                        .border(
                            width = 1.dp,
                            color = graphite2,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    selectedPage = state.selectedPage,
                    targetPage = pagerState.getTargetPage(),
                    tabs = tabs,
                    onTabClickListener = {
                        onTabClickListener(it)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    },
                    tabIndicator = {
                        TabRowIndicator(tabPositions = it, pagerState = pagerState)
                    }
                )
                MediumSpacer()
                Row(
                    horizontalArrangement = Arrangement.End, modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    TextButton(
                        text = stringResource(R.string.common_cancel),
                        onClick = onCancelClickListener
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(
                        text = stringResource(R.string.common_continue),
                        onClick = onContinueClickListener
                    )
                }
                HorizontalPager(count = tabs.size, state = pagerState) {
                }
            }
        }
    }
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun SwitcherScreenPreview() {
    SwitcherScreen(
        SwitcherStore.State(
            switcherData = SwitcherDomain(
                titleId = R.string.switcher_accounting_object_status,
                values = listOf(
                    InventoryAccountingObjectStatus.NOT_FOUND,
                    InventoryAccountingObjectStatus.FOUND
                ),
                currentValue = InventoryAccountingObjectStatus.FOUND,
                entityId = "123"
            ),
            selectedPage = 1,
        ), AppInsets(), {}, {}, {}, {})
}