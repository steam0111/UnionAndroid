package com.itrocket.union.identify.presentation.view

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
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailResult
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailComposeFragment
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectResult
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragment
import com.itrocket.union.identify.IdentifyModule.IDENTIFY_VIEW_MODEL_QUALIFIER
import com.itrocket.union.identify.presentation.store.IdentifyStore
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateComposeFragment
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuResult
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuFragment.Companion.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_CODE
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuFragment.Companion.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_LABEL
import com.itrocket.union.utils.flow.window
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient_impl.platform.entry.ReadingMode
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class IdentifyComposeFragment :
    BaseComposeFragment<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label>(
        IDENTIFY_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<IdentifyComposeFragmentArgs>()

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            lifecycleScope.launch(Dispatchers.Main) {
                accept(IdentifyStore.Intent.OnErrorHandled(throwable))
            }
        }

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_CODE,
                resultLabel = SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_LABEL,
                resultAction = {
                    (it as SelectActionWithValuesBottomMenuResult?)?.let {
                        accept(IdentifyStore.Intent.OnAccountingObjectActionsResultHandled(it))
                    }
                }
            ),
            FragmentResult(
                resultCode = AccountingObjectComposeFragment.ACCOUNTING_OBJECT_RESULT_CODE,
                resultLabel = AccountingObjectComposeFragment.ACCOUNTING_OBJECT_RESULT,
                resultAction = {
                    (it as AccountingObjectResult?)?.accountingObject?.let {
                        accept(
                            IdentifyStore.Intent.OnAccountingObjectSelected(it)
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
                            IdentifyStore.Intent.OnReadingModeTabChanged(it)
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
                            IdentifyStore.Intent.OnManualInput(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = AccountingObjectDetailComposeFragment.ACCOUNTING_OBJECT_DETAIL_RESULT_CODE,
                resultLabel = AccountingObjectDetailComposeFragment.ACCOUNTING_OBJECT_DETAIL_RESULT_LABEL,
                resultAction = {
                    (it as AccountingObjectDetailResult?)?.let {
                        accept(
                            IdentifyStore.Intent.OnAccountingObjectClosed
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
                                IdentifyStore.Intent.OnNewBarcodeHandled(
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
                                    IdentifyStore.Intent.OnNewRfidHandled(
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


    override fun renderState(
        state: IdentifyStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            IdentifyScreen(
                state = state,
                appInsets = appInsets,
                onReadingModeClickListener = {
                    accept(IdentifyStore.Intent.OnReadingModeClicked)
                },
                onBackClickListener = {
                    accept(IdentifyStore.Intent.OnBackClicked)
                },
                onObjectClickListener = {
                    accept(IdentifyStore.Intent.OnItemClicked(it))
                },
                onDropClickListener = {
                    accept(IdentifyStore.Intent.OnDropClicked)
                },
                onPageChanged = {
                    accept(IdentifyStore.Intent.OnSelectPage(it))
                },
                onPlusClickListener = {
                    accept(IdentifyStore.Intent.OnPlusClicked)
                },
                onListActionDialogDismissed = {
                    accept(IdentifyStore.Intent.OnListActionDialogDismissed)
                },
                onListActionDialogClick = {
                    accept(IdentifyStore.Intent.OnListActionDialogClicked(it))
                },
                onNomenclatureReserveClickListener = {
                    accept(IdentifyStore.Intent.OnNomenclatureReserveClicked(it))
                }
            )
        }
    }
}
