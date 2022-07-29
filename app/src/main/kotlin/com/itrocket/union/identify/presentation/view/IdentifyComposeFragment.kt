package com.itrocket.union.identify.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectResult
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragment
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuResult
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment.Companion.BOTTOM_ACTION_RESULT_CODE
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment.Companion.BOTTOM_ACTION_RESULT_LABEL
import com.itrocket.union.identify.IdentifyModule.IDENTIFY_VIEW_MODEL_QUALIFIER
import com.itrocket.union.identify.presentation.store.IdentifyStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient.domain.reader.ReaderMode
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class IdentifyComposeFragment :
    BaseComposeFragment<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label>(
        IDENTIFY_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<IdentifyComposeFragmentArgs>()

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = BOTTOM_ACTION_RESULT_CODE,
                resultLabel = BOTTOM_ACTION_RESULT_LABEL,
                resultAction = {
                    (it as BottomActionMenuResult?)?.type?.let {
//                    (it as BottomActionMenuResult?)?.type?.let {
                        accept(IdentifyStore.Intent.OnDeleteFromBottomAction(it))
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
            )
        )

    private val serviceEntryManager: ServiceEntryManager by inject()

    private val scanningDataRfid: MutableSet<String> = mutableSetOf()
    private var scanningDataBarcode: String = ""

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
                    scanningDataBarcode = it.data
                }
            }
            launch {
                serviceEntryManager.epcInventoryDataFlow.collect {
                    scanningDataRfid.add(it)
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
                        if (scanningDataRfid.isNotEmpty()) {
                            accept(
                                IdentifyStore.Intent.OnNewAccountingObjectRfidsHandled(
                                    scanningDataRfid.toList()
                                )
                            )
                        } else {
                            accept(
                                IdentifyStore.Intent.OnNewAccountingObjectBarcodeHandled(
                                    scanningDataBarcode
                                )
                            )
                        }
                        scanningDataRfid.clear()
                        scanningDataBarcode = ""
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
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
                onSaveClickListener = {
                    accept(IdentifyStore.Intent.OnSaveClicked)
                },
                onObjectClickListener = {
                    accept(IdentifyStore.Intent.OnItemClicked(it))
                },
//                onOSClickListener = { accept(IdentifyStore.Intent.OnOSClicked(it)) },
                onDropClickListener = {
                    accept(IdentifyStore.Intent.OnDropClicked)
                },
                onPageChanged = {
                    accept(IdentifyStore.Intent.OnSelectPage(it))
                }
            )
        }
    }
}
