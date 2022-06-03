package com.itrocket.union.moduleSettings.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.moduleSettings.ModuleSettingsModule.MODULESETTINGS_VIEW_MODEL_QUALIFIER
import com.itrocket.union.moduleSettings.presentation.store.ModuleSettingsStore

class ModuleSettingsComposeFragment :
    BaseComposeFragment<ModuleSettingsStore.Intent, ModuleSettingsStore.State, ModuleSettingsStore.Label>(
        MODULESETTINGS_VIEW_MODEL_QUALIFIER
    ) {

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
                }
            )
        }
    }
}