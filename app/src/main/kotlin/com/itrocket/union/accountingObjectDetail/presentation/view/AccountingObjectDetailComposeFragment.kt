package com.itrocket.union.accountingObjectDetail.presentation.view

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.accountingObjectDetail.AccountingObjectDetailModule.ACCOUNTINGOBJECTDETAIL_VIEW_MODEL_QUALIFIER
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStore
import com.itrocket.union.changeScanData.presentation.store.ChangeScanDataResult
import com.itrocket.union.changeScanData.presentation.view.ChangeScanDataComposeFragment
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class AccountingObjectDetailComposeFragment :
    BaseComposeFragment<AccountingObjectDetailStore.Intent, AccountingObjectDetailStore.State, AccountingObjectDetailStore.Label>(
        ACCOUNTINGOBJECTDETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<AccountingObjectDetailComposeFragmentArgs>()

    private val serviceEntryManager: ServiceEntryManager by inject()

    private var scanJob: Job? = null

    override val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(AccountingObjectDetailStore.Intent.OnBackClicked)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeScanning()
    }

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = ReadingModeComposeFragment.READING_MODE_TAB_RESULT_CODE,
                resultLabel = ReadingModeComposeFragment.READING_MODE_TAB_RESULT_LABEL,
                resultAction = {
                    (it as ReadingModeTab?)?.let {
                        accept(
                            AccountingObjectDetailStore.Intent.OnReadingModeTabChanged(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = ChangeScanDataComposeFragment.CHANGE_SCAN_DATA_RESULT_CODE,
                resultLabel = ChangeScanDataComposeFragment.CHANGE_SCAN_DATA_RESULT_LABEL,
                resultAction = {
                    (it as? ChangeScanDataResult)?.let {
                        accept(AccountingObjectDetailStore.Intent.OnMarkingClosed)
                    }
                }
            ),
            FragmentResult(
                resultCode = ReadingModeComposeFragment.READING_MODE_MANUAL_RESULT_CODE,
                resultLabel = ReadingModeComposeFragment.READING_MODE_MANUAL_RESULT_LABEL,
                resultAction = {
                    (it as ReadingModeResult?)?.let {
                        accept(
                            AccountingObjectDetailStore.Intent.OnManualInput(it)
                        )
                    }
                }
            ),
        )

    override fun renderState(
        state: AccountingObjectDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            AccountingObjectDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnBackClicked)
                },
                onReadingModeClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnReadingModeClicked)
                },
                onDocumentSearchClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnDocumentSearchClicked)
                },
                onDocumentAddClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnDocumentAddClicked)
                },
                onPageChangeListener = {
                    accept(AccountingObjectDetailStore.Intent.OnPageSelected(it))
                },
                onGenerateRfidClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnGenerateRfidClicked)
                },
                onWriteEpcTagClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnWriteEpcClicked)
                },
                onWriteEpcDismiss = {
                    accept(AccountingObjectDetailStore.Intent.OnDismissed)
                },
                onWriteOffClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnWriteOffClicked)
                },
                onRemoveBarcodeClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnRemoveBarcodeClicked)
                },
                onRemoveRfidClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnRemoveRfidClicked)
                }
            )
        }
    }

    override fun handleLabel(label: AccountingObjectDetailStore.Label) {
        super.handleLabel(label)
        if (label is AccountingObjectDetailStore.Label.ChangeSubscribeScanData) {
            if (label.isSubscribe) {
                observeScanning()
            } else {
                scanJob?.cancel()
                scanJob = null
            }
        }
    }

    private fun observeScanning() {
        scanJob = lifecycleScope.launch(Dispatchers.IO) {
            launch {
                observeTriggerPress()
            }
            launch {
                serviceEntryManager.barcodeScanDataFlow.collect {
                    withContext(Dispatchers.Main) {
                        accept(
                            AccountingObjectDetailStore.Intent.OnScanHandled(
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
                            AccountingObjectDetailStore.Intent.OnScanHandled(
                                it
                            )
                        )
                        serviceEntryManager.stopRfidOperation()
                    }
                }
            }
            launch {
                serviceEntryManager.writeEpcFlow.collect { result ->
                    withContext(Dispatchers.Main) {
                        accept(AccountingObjectDetailStore.Intent.OnWriteEpcHandled(result))
                        serviceEntryManager.stopRfidOperation()
                    }
                }
            }
            launch {
                serviceEntryManager.rfidError.collect { result ->
                    withContext(Dispatchers.Main) {
                        accept(AccountingObjectDetailStore.Intent.OnWriteEpcError(result))
                        serviceEntryManager.stopRfidOperation()
                    }
                }
            }
        }
    }

    private suspend fun observeTriggerPress() {
        serviceEntryManager.triggerPressFlow.collect {
            withContext(Dispatchers.Main) {
                when (it) {
                    TriggerEvent.Pressed -> {
                        accept(AccountingObjectDetailStore.Intent.OnTriggerPressed)
                    }
                    TriggerEvent.Released -> {
                        accept(AccountingObjectDetailStore.Intent.OnTriggerReleased)
                    }
                }
            }
        }
    }

    companion object {
        const val ACCOUNTING_OBJECT_DETAIL_RESULT_CODE = "accounting object detail result code"
        const val ACCOUNTING_OBJECT_DETAIL_RESULT_LABEL = "accounting object detail result label"
    }
}