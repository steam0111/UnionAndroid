package com.itrocket.union.equipmentTypes.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain

interface EquipmentTypeStore :
    Store<EquipmentTypeStore.Intent, EquipmentTypeStore.State, EquipmentTypeStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnItemClicked(val type: EquipmentTypesDomain) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val types: List<EquipmentTypesDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}