package com.itrocket.union.equipmentTypes.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.equipmentTypes.EquipmentTypeModule.EQUIPMENT_TYPES_MODEL_QUALIFIER
import com.itrocket.union.equipmentTypes.presentation.store.EquipmentTypeStore

class EquipmentTypeComposeFragment :
    BaseComposeFragment<EquipmentTypeStore.Intent, EquipmentTypeStore.State, EquipmentTypeStore.Label>(
        EQUIPMENT_TYPES_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: EquipmentTypeStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            EquipmentTypesScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(EquipmentTypeStore.Intent.OnBackClicked)
                },
                onItemClickListener = {
                    accept(EquipmentTypeStore.Intent.OnItemClicked(it.id))
                }
            )
        }
    }
}