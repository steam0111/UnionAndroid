package com.itrocket.union.equipmentTypes.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.departmentDetail.presentation.store.DepartmentDetailArguments
import com.itrocket.union.departments.presentation.store.DepartmentStore
import com.itrocket.union.departments.presentation.view.DepartmentComposeFragmentDirections
import com.itrocket.union.equipmentTypeDetail.presentation.store.EquipmentTypeDetailArguments
import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain
import com.itrocket.union.equipmentTypes.presentation.view.EquipmentTypeComposeFragmentDirections

interface EquipmentTypeStore :
    Store<EquipmentTypeStore.Intent, EquipmentTypeStore.State, EquipmentTypeStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnItemClicked(val id: String) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object OnSearchClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val types: List<EquipmentTypesDomain> = listOf(),
        val isShowSearch: Boolean = false,
        val searchText: String = ""
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = EquipmentTypeComposeFragmentDirections.toEquipmentTypeDetail(
                    EquipmentTypeDetailArguments(id = id)
                )
        }
    }
}