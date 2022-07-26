package com.itrocket.union.employeeDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain

interface EmployeeDetailStore :
    Store<EmployeeDetailStore.Intent, EmployeeDetailStore.State, EmployeeDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: EmployeeDetailDomain = EmployeeDetailDomain(),
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(),
            DefaultNavigationErrorLabel
    }
}