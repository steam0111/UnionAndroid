package com.itrocket.union.inventoryCreate.presentation.view

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import com.itrocket.union.inventoryCreate.InventoryCreateModule.INVENTORYCREATE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import androidx.navigation.fragment.navArgs
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateComposeFragmentArgs
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectResult
import com.itrocket.union.newAccountingObject.presentation.view.NewAccountingObjectComposeFragment
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
    override val navArgs by navArgs<InventoryCreateComposeFragmentArgs>()

    private val serviceEntryManager: ServiceEntryManager by inject()

    private val scanningData: MutableSet<String> = mutableSetOf()

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
                    scanningData.add(it.data)
                }
            }
            launch {
                serviceEntryManager.epcInventoryDataFlow.collect {
                    scanningData.add(it)
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
                    withContext(Dispatchers.Main) {
                        accept(
                            InventoryCreateStore.Intent.OnNewAccountingObjectsHandled(scanningData.toList())
                        )
                        scanningData.clear()
                    }
                }
            }
        }
    }
}