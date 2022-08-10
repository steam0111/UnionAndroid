package com.itrocket.union.changeScanData.presentation.view

import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.changeScanData.ChangeScanDataModule.CHANGESCANDATA_VIEW_MODEL_QUALIFIER
import com.itrocket.union.changeScanData.presentation.store.ChangeScanDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient.domain.reader.ReaderMode
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ChangeScanDataComposeFragment :
    BaseComposeBottomSheet<ChangeScanDataStore.Intent, ChangeScanDataStore.State, ChangeScanDataStore.Label>(
        CHANGESCANDATA_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    private val serviceEntryManager: ServiceEntryManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeScanning()
    }

    override fun renderState(
        state: ChangeScanDataStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ChangeScanDataScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(ChangeScanDataStore.Intent.OnBackClicked)
                },
                onApplyClickListener = {
                    accept(ChangeScanDataStore.Intent.OnApplyClicked)
                },
                onCancelClickListener = {
                    accept(ChangeScanDataStore.Intent.OnCancelClicked)
                },
                onReaderPowerClickListener = {
                    accept(ChangeScanDataStore.Intent.OnPowerClicked)
                }
            )
        }
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
                            ChangeScanDataStore.Intent.OnScanning(
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
                            ChangeScanDataStore.Intent.OnScanning(
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
                    if (serviceEntryManager.currentMode == ReaderMode.RFID) {
                        serviceEntryManager.epcInventory()
                    } else {
                        serviceEntryManager.startBarcodeScan()
                    }
                }
                TriggerEvent.Released -> {
                    if (serviceEntryManager.currentMode == ReaderMode.RFID) {
                        serviceEntryManager.stopRfidOperation()
                    } else {
                        serviceEntryManager.stopBarcodeScan()
                    }
                }
            }
        }
    }

    companion object {
        const val CHANGE_SCAN_DATA_ARGS = "change scan data args"
    }
}