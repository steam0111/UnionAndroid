package com.itrocket.union.documentCreate.presentation.view

import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectResult
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragment
import com.itrocket.union.documentCreate.DocumentCreateModule.DOCUMENTCREATE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStore
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.location.presentation.view.LocationComposeFragment
import com.itrocket.union.reserves.presentation.store.ReservesResult
import com.itrocket.union.reserves.presentation.view.ReservesComposeFragment
import com.itrocket.union.selectCount.presentation.store.SelectCountResult
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient.domain.reader.ReaderMode
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class DocumentCreateComposeFragment :
    BaseComposeFragment<DocumentCreateStore.Intent, DocumentCreateStore.State, DocumentCreateStore.Label>(
        DOCUMENTCREATE_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<DocumentCreateComposeFragmentArgs>()

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = SelectParamsComposeFragment.SELECT_PARAMS_RESULT_CODE,
                resultLabel = SelectParamsComposeFragment.SELECT_PARAMS_RESULT,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(
                            DocumentCreateStore.Intent.OnParamsChanged(it)
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
                            DocumentCreateStore.Intent.OnAccountingObjectSelected(it)
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
                            DocumentCreateStore.Intent.OnReserveSelected(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = LocationComposeFragment.LOCATION_RESULT_CODE,
                resultLabel = LocationComposeFragment.LOCATION_RESULT,
                resultAction = {
                    (it as LocationResult?)?.let {
                        accept(
                            DocumentCreateStore.Intent.OnLocationChanged(it)
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
                            DocumentCreateStore.Intent.OnReserveCountSelected(it)
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

    override fun renderState(
        state: DocumentCreateStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            DocumentCreateScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(DocumentCreateStore.Intent.OnBackClicked)
                },
                onDropClickListener = {
                    accept(DocumentCreateStore.Intent.OnDropClicked)
                },
                onParamClickListener = {
                    accept(DocumentCreateStore.Intent.OnParamClicked(it))
                },
                onPageChanged = {
                    accept(DocumentCreateStore.Intent.OnSelectPage(it))
                },
                onParamCrossClickListener = {
                    accept(DocumentCreateStore.Intent.OnParamCrossClicked(it))
                },
                onSaveClickListener = {
                    accept(DocumentCreateStore.Intent.OnSaveClicked)
                },
                onChooseAccountingObjectClickListener = {
                    accept(DocumentCreateStore.Intent.OnChooseAccountingObjectClicked)
                },
                onChooseReserveClickListener = {
                    accept(DocumentCreateStore.Intent.OnChooseReserveClicked)
                },
                onSettingsClickListener = {
                    accept(DocumentCreateStore.Intent.OnSettingsClicked)
                },
                onPrevClickListener = {
                    accept(DocumentCreateStore.Intent.OnPrevClicked)
                },
                onNextClickListener = {
                    accept(DocumentCreateStore.Intent.OnNextClicked)
                },
                onConductClickListener = {
                    accept(DocumentCreateStore.Intent.OnCompleteClicked)
                },
                onReserveClickListener = {
                    accept(DocumentCreateStore.Intent.OnReserveClicked(it))
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
                                DocumentCreateStore.Intent.OnNewAccountingObjectRfidsHandled(
                                    scanningDataRfid.toList()
                                )
                            )
                        } else {
                            accept(
                                DocumentCreateStore.Intent.OnNewAccountingObjectBarcodeHandled(
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
}