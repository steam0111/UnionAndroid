package com.itrocket.union.transit.presentation.view

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectResult
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragment
import com.itrocket.union.dataCollect.presentation.store.DataCollectStore
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateComposeFragment
import com.itrocket.union.nfcReader.presentation.store.NfcReaderResult
import com.itrocket.union.nfcReader.presentation.view.NfcReaderComposeFragment
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserves.presentation.store.ReservesResult
import com.itrocket.union.reserves.presentation.view.ReservesComposeFragment
import com.itrocket.union.selectCount.presentation.store.SelectCountResult
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragment
import com.itrocket.union.transit.TransitModule.TRANSIT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.transit.presentation.store.TransitStore
import com.itrocket.union.utils.flow.window
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient_impl.platform.entry.ReadingMode
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class TransitComposeFragment :
    BaseComposeFragment<TransitStore.Intent, TransitStore.State, TransitStore.Label>(
        TRANSIT_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<TransitComposeFragmentArgs>()

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            lifecycleScope.launch(Dispatchers.Main) {
                accept(TransitStore.Intent.OnErrorHandled(throwable))
            }
        }

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = SelectParamsComposeFragment.SELECT_PARAMS_RESULT_CODE,
                resultLabel = SelectParamsComposeFragment.SELECT_PARAMS_RESULT,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(
                            TransitStore.Intent.OnParamsChanged(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = AccountingObjectComposeFragment.ACCOUNTING_OBJECT_RESULT_CODE,
                resultLabel = AccountingObjectComposeFragment.ACCOUNTING_OBJECT_RESULT,
                resultAction = {
                    (it as AccountingObjectResult?)?.accountingObject?.let {
                        accept(
                            TransitStore.Intent.OnAccountingObjectSelected(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = ReservesComposeFragment.RESERVES_RESULT_CODE,
                resultLabel = ReservesComposeFragment.RESERVES_RESULT_LABEL,
                resultAction = {
                    (it as ReservesResult?)?.reserve?.let {
                        accept(
                            TransitStore.Intent.OnReserveSelected(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = SelectCountComposeFragment.SELECT_COUNT_RESULT_CODE,
                resultLabel = SelectCountComposeFragment.SELECT_COUNT_RESULT_LABEL,
                resultAction = {
                    (it as SelectCountResult?)?.let {
                        accept(
                            TransitStore.Intent.OnReserveCountSelected(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = NfcReaderComposeFragment.NFC_READER_RESULT_CODE,
                resultLabel = NfcReaderComposeFragment.NFC_READER_RESULT_LABEL,
                resultAction = {
                    (it as NfcReaderResult?)?.let {
                        accept(
                            TransitStore.Intent.OnNfcReaderClose(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = ReadingModeComposeFragment.READING_MODE_TAB_RESULT_CODE,
                resultLabel = ReadingModeComposeFragment.READING_MODE_TAB_RESULT_LABEL,
                resultAction = {
                    (it as ReadingModeTab?)?.let {
                        accept(
                            TransitStore.Intent.OnReadingModeTabChanged(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = ReadingModeComposeFragment.READING_MODE_MANUAL_RESULT_CODE,
                resultLabel = ReadingModeComposeFragment.READING_MODE_MANUAL_RESULT_LABEL,
                resultAction = {
                    (it as ReadingModeResult?)?.let {
                        accept(
                            TransitStore.Intent.OnManualInput(it)
                        )
                    }
                }
            ),
        )

    private val serviceEntryManager: ServiceEntryManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeScanning()
    }

    override fun renderState(
        state: TransitStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            TransitScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(TransitStore.Intent.OnBackClicked)
                },
                onDropClickListener = {
                    accept(TransitStore.Intent.OnDropClicked)
                },
                onParamClickListener = {
                    accept(TransitStore.Intent.OnParamClicked(it))
                },
                onPageChanged = {
                    accept(TransitStore.Intent.OnSelectPage(it))
                },
                onParamCrossClickListener = {
                    accept(TransitStore.Intent.OnParamCrossClicked(it))
                },
                onSaveClickListener = {
                    accept(TransitStore.Intent.OnSaveClicked)
                },
                onChooseAccountingObjectClickListener = {
                    accept(TransitStore.Intent.OnChooseAccountingObjectClicked)
                },
                onChooseReserveClickListener = {
                    accept(TransitStore.Intent.OnChooseReserveClicked)
                },
                onSettingsClickListener = {
                    accept(TransitStore.Intent.OnSettingsClicked)
                },
                onDismissConfirmDialog = {
                    accept(TransitStore.Intent.OnDismissConfirmDialog)
                },
                onConfirmActionClick = {
                    accept(TransitStore.Intent.OnConfirmActionClick)
                },
                onConductClickListener = {
                    accept(TransitStore.Intent.OnCompleteClicked)
                },
                onReserveClickListener = {
                    accept(TransitStore.Intent.OnReserveClicked(it))
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
                                TransitStore.Intent.OnNewAccountingObjectBarcodeHandled(
                                    it.data
                                )
                            )
                        }
                    }
                }
                launch {
                    serviceEntryManager
                        .epcInventoryDataFlow
                        .distinctUntilChanged()
                        .window(
                            maxBufferSize = InventoryCreateComposeFragment.WINDOW_MAX_BUFFER_SIZE,
                            maxMsWaitTime = InventoryCreateComposeFragment.WINDOW_MAX_MS_WAIT_TIME,
                            bufferOnlyUniqueValues = InventoryCreateComposeFragment.WINDOW_BUFFER_ONLY_UNIQUE_VALUES
                        )
                        .collect {
                            withContext(Dispatchers.Main) {
                                accept(
                                    TransitStore.Intent.OnNewAccountingObjectRfidHandled(
                                        it
                                    )
                                )
                            }
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
}