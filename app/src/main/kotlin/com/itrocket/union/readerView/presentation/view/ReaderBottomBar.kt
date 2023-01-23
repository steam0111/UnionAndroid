package com.itrocket.union.readerView.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itrocket.union.readerView.presentation.store.ReaderBottomBarStore
import com.itrocket.union.readerView.presentation.store.ReaderBottomBarViewModel
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.ui.ReadingModeButton

@Composable
fun ReaderBottomBar(
    viewModel: ReaderBottomBarViewModel,
    selectedReadingMode: ReadingModeTab,
    onReadingModeClickListener: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), contentAlignment = Alignment.BottomEnd
    ) {
        val state = viewModel.state.collectAsState(initial = ReaderBottomBarStore.State()).value
        ReadingModeButton(
            readingModeTab = selectedReadingMode,
            onClick = onReadingModeClickListener,
            rfidLevel = state.readerPower
        )
    }
}

