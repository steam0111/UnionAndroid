package com.itrocket.union.inventoryCreate.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailResult
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailComposeFragment
import com.itrocket.union.comment.presentation.store.CommentResult
import com.itrocket.union.comment.presentation.view.CommentComposeFragment
import com.itrocket.union.inventoryChoose.presentation.store.InventoryChooseResult
import com.itrocket.union.inventoryChoose.presentation.view.InventoryChooseComposeFragment
import com.itrocket.union.inventoryCreate.InventoryCreateModule.INVENTORYCREATE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateStore
import com.itrocket.union.inventoryCreate.presentation.store.toInventoryCreateUiState
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.utils.flow.window
import com.itrocket.union.utils.fragment.ChildBackPressedHandler
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

class InventoryCreateComposeFragment :
    BaseComposeFragment<InventoryCreateStore.Intent, InventoryCreateStore.State, InventoryCreateStore.Label>(
        INVENTORYCREATE_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(InventoryCreateStore.Intent.OnBackClicked)
            }
        }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            lifecycleScope.launch(Dispatchers.Main) {
                accept(InventoryCreateStore.Intent.OnErrorHandled(throwable))
            }
        }

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = ReadingModeComposeFragment.READING_MODE_TAB_RESULT_CODE,
                resultLabel = ReadingModeComposeFragment.READING_MODE_TAB_RESULT_LABEL,
                resultAction = {
                    (it as ReadingModeTab?)?.let {
                        accept(
                            InventoryCreateStore.Intent.OnReadingModeTabChanged(it)
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
                            InventoryCreateStore.Intent.OnManualInput(it)
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
                            InventoryCreateStore.Intent.OnAccountingObjectChanged(it.accountingObject)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = InventoryChooseComposeFragment.INVENTORY_CHOOSE_RESULT_CODE,
                resultLabel = InventoryChooseComposeFragment.INVENTORY_CHOOSE_RESULT_LABEL,
                resultAction = {
                    (it as InventoryChooseResult?)?.let {
                        accept(
                            InventoryCreateStore.Intent.OnInventoryChooseResultHandled(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = CommentComposeFragment.COMMENT_RESULT_CODE,
                resultLabel = CommentComposeFragment.COMMENT_RESULT_LABEL,
                resultAction = {
                    (it as CommentResult?)?.let {
                        accept(
                            InventoryCreateStore.Intent.OnCommentResultHandled(it)
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
        state: InventoryCreateStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            InventoryCreateScreen(
                state = state.toInventoryCreateUiState(),
                appInsets = appInsets,
                onBackClickListener = {
                    accept(InventoryCreateStore.Intent.OnBackClicked)
                },
                onAccountingObjectClickListener = {
                    accept(InventoryCreateStore.Intent.OnAccountingObjectClicked(it))
                },
                onSaveClickListener = {
                    accept(InventoryCreateStore.Intent.OnSaveClicked)
                },
                onAddNewChanged = {
                    accept(InventoryCreateStore.Intent.OnAddNewClicked)
                },
                onDropClickListener = {
                    accept(InventoryCreateStore.Intent.OnDropClicked)
                },
                onHideFoundAccountingObjectChanged = {
                    accept(InventoryCreateStore.Intent.OnHideFoundAccountingObjectClicked)
                },
                onReadingClickListener = {
                    accept(InventoryCreateStore.Intent.OnReadingClicked)
                },
                onFinishClickListener = {
                    accept(InventoryCreateStore.Intent.OnCompleteClicked)
                },
                onSaveDismissClickListener = {
                    accept(InventoryCreateStore.Intent.OnSaveDismissed)
                },
                onSaveConfirmClickListener = {
                    accept(InventoryCreateStore.Intent.OnSaveConfirmed)
                },
                onCompleteConfirmClickListener = {
                    accept(InventoryCreateStore.Intent.OnCompleteConfirmed)
                },
                onCompleteDismissClickListener = {
                    accept(InventoryCreateStore.Intent.OnCompleteDismissed)
                },
                onSearchTextChanged = {
                    accept(InventoryCreateStore.Intent.OnSearchTextChanged(it))
                },
                onSearchClickListener = {
                    accept(InventoryCreateStore.Intent.OnSearchClicked)
                },
                onDeleteConfirmClickListener = {
                    accept(InventoryCreateStore.Intent.OnDeleteConfirmClicked)
                },
                onDeleteDismissClickListener = {
                    accept(InventoryCreateStore.Intent.OnDeleteDismissClicked)
                },
                onStatusClickListener = {
                    accept(InventoryCreateStore.Intent.OnStatusClicked(it))
                },
                onAccountingObjectLongClickListener = {
                    accept(InventoryCreateStore.Intent.OnAccountingObjectLongClicked(it))
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
                    serviceEntryManager
                        .barcodeScanDataFlow
                        .collect {
                            withContext(Dispatchers.Main) {
                                accept(
                                    InventoryCreateStore.Intent.OnNewAccountingObjectBarcodeHandled(
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
                            maxBufferSize = WINDOW_MAX_BUFFER_SIZE,
                            maxMsWaitTime = WINDOW_MAX_MS_WAIT_TIME,
                            bufferOnlyUniqueValues = WINDOW_BUFFER_ONLY_UNIQUE_VALUES
                        )
                        .collect { rfids ->
                            withContext(Dispatchers.Main) {
                                accept(
                                    InventoryCreateStore.Intent.OnNewAccountingObjectRfidHandled(
                                        rfids
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

    override fun handleLabel(label: InventoryCreateStore.Label) {
        super.handleLabel(label)
        when (label) {
            is InventoryCreateStore.Label.GoBack -> (parentFragment as? ChildBackPressedHandler)?.onChildBackPressed()
        }

    }

    companion object {
        const val WINDOW_MAX_BUFFER_SIZE = 20
        const val WINDOW_MAX_MS_WAIT_TIME = 300L
        const val WINDOW_BUFFER_ONLY_UNIQUE_VALUES = true
        const val INVENTORY_CREATE_ARGUMENTS = "inventory create arguments"
    }
}