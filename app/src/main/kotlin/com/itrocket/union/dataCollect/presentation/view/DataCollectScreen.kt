package com.itrocket.union.dataCollect.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.dataCollect.presentation.store.DataCollectStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.BottomLine
import com.itrocket.union.ui.ReadingModeBottomBar
import com.itrocket.union.ui.ScanningObjectItem
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DataCollectScreen(
    state: DataCollectStore.State,
    appInsets: AppInsets,
    onReadingModeClickListener: () -> Unit,
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit
) {
    val pagerState = rememberPagerState(state.selectedPage)
    val coroutineScope = rememberCoroutineScope()
    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onBackClickListener = onBackClickListener,
                    onDropClickListener = onDropClickListener
                )
            },
            bottomBar = {
                ReadingModeBottomBar(
                    readingModeTab = state.readingModeTab,
                    onReadingModeClickListener = onReadingModeClickListener,
                    rfidLevel = state.readerPower
                )
            },
            content = {
                Content(
                    coroutineScope = coroutineScope,
                    state = state,
                    paddingValues = it,
                    pagerState = pagerState,
                    scanningObjects = state.scanningObjects,
                    onReadingModeClickListener = onReadingModeClickListener
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Content(
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    state: DataCollectStore.State,
    onReadingModeClickListener: () -> Unit,
    paddingValues: PaddingValues,
    scanningObjects: List<String>
) {
    Scaffold(content = {
        if (state.scanningObjects.isNotEmpty()) {
            ScanningObjectIsNotEmptyScreen(
                scanningObjects = scanningObjects,
                paddingValues = paddingValues
            )
        } else ObjectListEmpty(paddingValues = PaddingValues())
    })
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit
) {
    BaseToolbar(
        title = stringResource(id = R.string.data_collect_title),
        onStartImageClickListener = onBackClickListener,
        startImageId = R.drawable.ic_arrow_back,
        textColor = white,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_delete),
                    contentDescription = null,
                    modifier = Modifier.clickableUnbounded(onClick = onDropClickListener)
                )
            }
        }
    )
}

@Composable
private fun ScanningObjectIsNotEmptyScreen(
    paddingValues: PaddingValues,
    scanningObjects: List<String>
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        itemsIndexed(scanningObjects)
        { index, item ->
            Column {
                ScanningObjectItem(
                    scanningObject = item
                )
                if (scanningObjects.lastIndex != index) {
                    BottomLine()
                }
            }
        }
    }
}

@Composable
private fun ObjectListEmpty(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_reader),
            contentDescription = null,
            colorFilter = ColorFilter.tint(graphite4)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.text_begin_scanning),
            style = AppTheme.typography.body1,
            color = graphite4,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun DataCollectScreenPreview() {
    DataCollectScreen(
        state = DataCollectStore.State(
            scanningObjects = listOf(
                "Barcode : 001364796215",
                "RFID : E1568122212215464651564654",
                "RFID : E1568122212215464651564654",
                "RFID : E1568122212215464651564654",
                "RFID : E1568122212215464651564654",
                "RFID : E1568122212215464651564654",
                "Barcode : 001364796215",
                "Barcode : 001364796215",
                "Barcode : 001364796215"
            )
        ),
        appInsets = AppInsets(),
        onReadingModeClickListener = {},
        onBackClickListener = {}) {
    }
}