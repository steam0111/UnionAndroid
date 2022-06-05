package com.itrocket.union.readingMode.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.readingMode.ReadingModeModule.READINGMODE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.readingMode.presentation.store.ReadingModeStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.interid.scannerclient.domain.reader.ReaderMode
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ReadingModeComposeFragment :
    BaseComposeBottomSheet<ReadingModeStore.Intent, ReadingModeStore.State, ReadingModeStore.Label>(
        READINGMODE_VIEW_MODEL_QUALIFIER
    ) {

    private val serviceEntryManager: ServiceEntryManager by inject()

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    override fun renderState(
        state: ReadingModeStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ReadingModeScreen(
                state = state,
                appInsets = appInsets,
                onCameraClickListener = {
                    accept(ReadingModeStore.Intent.OnCameraClicked)
                },
                onManualInputClickListener = {
                    accept(ReadingModeStore.Intent.OnManualInputClicked)
                },
                onSettingsClickListener = {
                    accept(ReadingModeStore.Intent.OnSettingsClicked)
                },
                onReadingModeTabClickListener = {
                    accept(ReadingModeStore.Intent.OnReadingModeSelected(it))
                }
            )
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            launch {
                serviceEntryManager.barcodeScanDataFlow.collect {
                    Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
                }
            }
            launch {
                serviceEntryManager.epcInventoryDataFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            launch {
                serviceEntryManager.triggerPressFlow.collect {
                    when (it) {
                        TriggerEvent.Pressed -> {
                            when (serviceEntryManager.currentMode) {
                                ReaderMode.BARCODE -> serviceEntryManager.startBarcodeScan()
                                ReaderMode.RFID -> serviceEntryManager.epcInventory()
                            }
                        }
                        TriggerEvent.Released -> {
                            when (serviceEntryManager.currentMode) {
                                ReaderMode.BARCODE -> serviceEntryManager.stopBarcodeScan()
                                ReaderMode.RFID -> serviceEntryManager.stopRfidOperation()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val READING_MODE_ARGS = "reading mode args"
    }
}