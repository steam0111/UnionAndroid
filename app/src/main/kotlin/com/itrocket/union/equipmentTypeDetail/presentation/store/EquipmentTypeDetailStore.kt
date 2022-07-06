package com.itrocket.union.equipmentTypeDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.equipmentTypeDetail.domain.entity.EquipmentTypeDetailDomain

interface EquipmentTypeDetailStore :
    Store<EquipmentTypeDetailStore.Intent, EquipmentTypeDetailStore.State, EquipmentTypeDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: EquipmentTypeDetailDomain,
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(),
            DefaultNavigationErrorLabel
    }
}