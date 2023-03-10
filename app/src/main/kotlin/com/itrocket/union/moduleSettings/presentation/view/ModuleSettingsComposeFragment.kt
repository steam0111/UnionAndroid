package com.itrocket.union.moduleSettings.presentation.view

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.moduleSettings.ModuleSettingsModule.MODULESETTINGS_VIEW_MODEL_QUALIFIER
import com.itrocket.union.moduleSettings.presentation.store.ModuleSettingsStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ModuleSettingsComposeFragment :
    BaseComposeFragment<ModuleSettingsStore.Intent, ModuleSettingsStore.State, ModuleSettingsStore.Label>(
        MODULESETTINGS_VIEW_MODEL_QUALIFIER
    ) {

    private val serviceEntryManager: ServiceEntryManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeKeyCode()
    }

    override fun renderState(
        state: ModuleSettingsStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ModuleSettingsScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(ModuleSettingsStore.Intent.OnBackClicked)
                },
                onDefineCursorClickListener = {
                    accept(ModuleSettingsStore.Intent.OnDefineCursorClicked)
                },
                onSaveClickListener = {
                    accept(ModuleSettingsStore.Intent.OnSaveClicked)
                },
                onDropdownItemClickListener = {
                    accept(ModuleSettingsStore.Intent.OnDropdownItemClicked(it))
                },
                onDropdownDismiss = {
                    accept(ModuleSettingsStore.Intent.OnDropdownDismiss)
                },
                onDropdownOpenClickListener = {
                    accept(ModuleSettingsStore.Intent.OnDropdownOpenClicked)
                },
                onPowerChanged = { accept(ModuleSettingsStore.Intent.OnPowerChanged(it)) },
                onArrowUpClickListener = { accept(ModuleSettingsStore.Intent.OnArrowUpClicked) },
                onArrowDownClickListener = { accept(ModuleSettingsStore.Intent.OnArrowDownClicked) },
                onDynamicSaveInventoryChanged = {
                    accept(
                        ModuleSettingsStore.Intent.OnDynamicSaveInventoryClicked(
                            it
                        )
                    )
                },
                onClearButtonClicked = { accept(ModuleSettingsStore.Intent.OnClearDbClicked) },
                onConfirmClearDbClickListener = { accept(ModuleSettingsStore.Intent.OnConfirmClearDbClicked) },
                onDismissClearClickListener = { accept(ModuleSettingsStore.Intent.OnDismissClearDbClicked) },
                onReadingTabClickListener = {
                    accept(ModuleSettingsStore.Intent.OnReadingModeTabClicked(it))
                },
                onSyncFileChecked = {
                    accept(ModuleSettingsStore.Intent.OnSyncFileSwitched(it))
                }
            )
        }
    }

    private fun observeKeyCode() {
        lifecycleScope.launch(Dispatchers.IO) {
            launch {
                serviceEntryManager.keyCode.collect {
                    withContext(Dispatchers.Main) {
                        accept(ModuleSettingsStore.Intent.OnCursorDefined(it ?: 0))
                    }
                }
            }
            launch {
                serviceEntryManager.defaultService.collect {
                    withContext(Dispatchers.Main) {
                        accept(
                            ModuleSettingsStore.Intent.OnDefaultServiceHandled(it.orEmpty())
                        )
                    }
                }
            }
            launch {
                serviceEntryManager.installedServices.collect {
                    withContext(Dispatchers.Main) {
                        accept(
                            ModuleSettingsStore.Intent.OnServicesHandled(it)
                        )
                    }
                }
            }
        }
    }

}