package com.itrocket.union.reserveDetail.presentation.view

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
import com.itrocket.union.labelType.presentation.store.LabelTypeResult
import com.itrocket.union.labelType.presentation.view.LabelTypeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserveDetail.ReserveDetailModule.RESERVEDETAIL_VIEW_MODEL_QUALIFIER
import com.itrocket.union.reserveDetail.presentation.store.ReserveDetailStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient_impl.platform.entry.TriggerEvent
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ReserveDetailComposeFragment :
    BaseComposeFragment<ReserveDetailStore.Intent, ReserveDetailStore.State, ReserveDetailStore.Label>(
        RESERVEDETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<ReserveDetailComposeFragmentArgs>()

    private val serviceEntryManager: ServiceEntryManager by inject()

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            lifecycleScope.launch(Dispatchers.Main) {
                accept(ReserveDetailStore.Intent.OnErrorHandled(throwable))
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
                            ReserveDetailStore.Intent.OnReadingModeTabChanged(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = LabelTypeComposeFragment.LABEL_TYPE_RESULT_CODE,
                resultLabel = LabelTypeComposeFragment.LABEL_TYPE_RESULT_LABEL,
                resultAction = {
                    (it as LabelTypeResult?)?.let {
                        accept(
                            ReserveDetailStore.Intent.OnLabelTypeSelected(it.labelTypeId)
                        )
                    }
                }
            )
        )

    override fun renderState(
        state: ReserveDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ReserveDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(ReserveDetailStore.Intent.OnBackClicked)
                },
                onReadingModeClickListener = {
                    accept(ReserveDetailStore.Intent.OnReadingModeClicked)
                },
                onDocumentSearchClickListener = {
                    accept(ReserveDetailStore.Intent.OnDocumentSearchClicked)
                },
                onDocumentAddClickListener = {
                    accept(ReserveDetailStore.Intent.OnDocumentAddClicked)
                },
                onMarkingClicked = {
                    accept(ReserveDetailStore.Intent.OnMarkingClicked)
                },
                onWriteEpcDismiss = {
                    accept(ReserveDetailStore.Intent.OnDismissed)
                },
                onLabelTypeEditClickListener = {
                    accept(ReserveDetailStore.Intent.OnLabelTypeEditClicked)
                }
            )
        }
    }

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
                    serviceEntryManager.writeEpcFlow.collect { result ->
                        withContext(Dispatchers.Main) {
                            accept(ReserveDetailStore.Intent.OnWriteEpcHandled(result))
                            serviceEntryManager.stopRfidOperation()
                        }
                    }
                }
                launch {
                    serviceEntryManager.rfidError.collect { result ->
                        withContext(Dispatchers.Main) {
                            accept(ReserveDetailStore.Intent.OnWriteEpcError(result))
                            serviceEntryManager.stopRfidOperation()
                        }
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
                        accept(ReserveDetailStore.Intent.OnTriggerPressed)
                    }
                    TriggerEvent.Released -> {
                        accept(ReserveDetailStore.Intent.OnTriggerReleased)
                    }
                }
            }
        }
    }
}