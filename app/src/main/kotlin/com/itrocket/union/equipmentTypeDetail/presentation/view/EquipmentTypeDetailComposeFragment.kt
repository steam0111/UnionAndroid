package com.itrocket.union.equipmentTypeDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.equipmentTypeDetail.EquipmentTypeDetailModule
import com.itrocket.union.equipmentTypeDetail.presentation.store.EquipmentTypeDetailStore

class EquipmentTypeDetailComposeFragment :
    BaseComposeFragment<EquipmentTypeDetailStore.Intent, EquipmentTypeDetailStore.State, EquipmentTypeDetailStore.Label>(
        EquipmentTypeDetailModule.EQUIPMENT_TYPE_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<EquipmentTypeDetailComposeFragmentArgs>()

    override fun renderState(
        state: EquipmentTypeDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            EquipmentTypeDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(EquipmentTypeDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}