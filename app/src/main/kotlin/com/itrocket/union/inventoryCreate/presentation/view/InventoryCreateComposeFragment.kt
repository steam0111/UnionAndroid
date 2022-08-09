package com.itrocket.union.inventoryCreate.presentation.view

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.inventoryCreate.InventoryCreateModule.INVENTORYCREATE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateStore
import com.itrocket.union.switcher.presentation.store.SwitcherResult
import com.itrocket.union.switcher.presentation.view.SwitcherComposeFragment
import com.itrocket.union.utils.fragment.ChildBackPressedHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient.domain.reader.ReaderMode
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

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultLabel = SwitcherComposeFragment.SWITCHER_RESULT,
                resultCode = SwitcherComposeFragment.SWITCHER_RESULT_CODE,
                resultAction = {
                    (it as? SwitcherResult?)?.let {
                        accept(InventoryCreateStore.Intent.OnAccountingObjectStatusChanged(it))
                    }
                }
            )
        )

    private val serviceEntryManager: ServiceEntryManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeScanning()
    }

    override fun renderState(
        state: InventoryCreateStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            InventoryCreateScreen(
                state = state,
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
                onInWorkClickListener = {
                    accept(InventoryCreateStore.Intent.OnInWorkClicked)
                },
                onDismissConfirmDialog = {
                    accept(InventoryCreateStore.Intent.OnDismissConfirmDialog)
                },
                onConfirmActionClick = {
                    accept(InventoryCreateStore.Intent.OnConfirmActionClick)
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
                            InventoryCreateStore.Intent.OnNewAccountingObjectBarcodeHandled(
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
                            InventoryCreateStore.Intent.OnNewAccountingObjectRfidHandled(
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

    override fun handleLabel(label: InventoryCreateStore.Label) {
        super.handleLabel(label)
        when (label) {
            InventoryCreateStore.Label.GoBack -> (parentFragment as? ChildBackPressedHandler)?.onChildBackPressed()
        }

    }

    companion object {
        const val INVENTORY_CREATE_ARGUMENTS = "inventory create arguments"
    }
}