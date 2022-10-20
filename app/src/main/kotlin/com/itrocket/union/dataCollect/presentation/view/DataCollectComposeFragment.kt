package com.itrocket.union.dataCollect.presentation.view

import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.dataCollect.DataCollectModule.DATA_COLLECT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.dataCollect.presentation.store.DataCollectStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient.domain.reader.ReaderMode
import ru.interid.scannerclient_impl.platform.entry.ReadingMode
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager


class DataCollectComposeFragment :
    BaseComposeFragment<DataCollectStore.Intent, DataCollectStore.State, DataCollectStore.Label>(
        DATA_COLLECT_VIEW_MODEL_QUALIFIER
    ) {
    private val serviceEntryManager: ServiceEntryManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeScanning()
    }

    private fun observeScanning() {
        lifecycleScope.launch(Dispatchers.IO) {
            launch {
                observeTriggerPress()
            }
            launch {
                serviceEntryManager.barcodeScanDataFlow.collect {
                    withContext(Dispatchers.Main) {
                        accept(
                            DataCollectStore.Intent.OnNewAccountingObjectBarcodeHandled(
                                it.data
                            )
                        )
                    }
                }
            }
            launch {
                serviceEntryManager.epcInventoryDataFlow.collect {
                    withContext(Dispatchers.Main) {
                        accept(
                            DataCollectStore.Intent.OnNewAccountingObjectRfidHandled(
                                it
                            )
                        )
                    }
                }
            }
        }
    }

    private suspend fun observeTriggerPress() {
        serviceEntryManager.triggerPressFlow.collect {
            when (it) {
                TriggerEvent.Pressed -> {
                    if (serviceEntryManager.currentMode == ReadingMode.RFID) {
                        serviceEntryManager.epcInventory()
                    } else {
                        serviceEntryManager.startBarcodeScan()
                    }
                }
                TriggerEvent.Released -> {
                    if (serviceEntryManager.currentMode == ReadingMode.RFID) {
                        serviceEntryManager.stopRfidOperation()
                    } else {
                        serviceEntryManager.stopBarcodeScan()
                    }
                }
            }
        }
    }

    override fun renderState(
        state: DataCollectStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            DataCollectScreen(
                state = state,
                appInsets = appInsets,
                onReadingModeClickListener = { accept(DataCollectStore.Intent.OnReadingModeClicked) },
                onBackClickListener = { accept((DataCollectStore.Intent.OnBackClicked)) },
                onDropClickListener = { accept(DataCollectStore.Intent.OnDropClicked) },
            )
        }
    }
}