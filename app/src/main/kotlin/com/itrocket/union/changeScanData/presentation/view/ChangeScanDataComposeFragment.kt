package com.itrocket.union.changeScanData.presentation.view

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.changeScanData.ChangeScanDataModule.CHANGESCANDATA_VIEW_MODEL_QUALIFIER
import com.itrocket.union.changeScanData.presentation.store.ChangeScanDataResult
import com.itrocket.union.changeScanData.presentation.store.ChangeScanDataStore
import com.itrocket.union.dataCollect.presentation.store.DataCollectStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient_impl.platform.entry.ReadingMode
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ChangeScanDataComposeFragment :
    BaseComposeBottomSheet<ChangeScanDataStore.Intent, ChangeScanDataStore.State, ChangeScanDataStore.Label>(
        CHANGESCANDATA_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    private val serviceEntryManager: ServiceEntryManager by inject()

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            lifecycleScope.launch(Dispatchers.Main) {
                accept(ChangeScanDataStore.Intent.OnErrorHandled(throwable))
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                },
                onScanDataTextChanged = {
                    accept(ChangeScanDataStore.Intent.OnScanDataChanged(it))
                }
            )
        }
    }

    private fun observeScanning() {
        lifecycleScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                            serviceEntryManager.stopBarcodeScan()
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
                            serviceEntryManager.stopRfidOperation()
                        }
                    }
                }
            }
        }
    }

    override fun dismiss() {
        setFragmentResult(
            CHANGE_SCAN_DATA_RESULT_CODE, bundleOf(
                CHANGE_SCAN_DATA_RESULT_LABEL to ChangeScanDataResult
            )
        )
        super.dismiss()
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

    companion object {
        const val CHANGE_SCAN_DATA_ARGS = "change scan data args"
        const val CHANGE_SCAN_DATA_RESULT_CODE = "change scan data result code"
        const val CHANGE_SCAN_DATA_RESULT_LABEL = "change scan data result label"
    }
}