package com.itrocket.union.employees.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.employeeDetail.presentation.store.EmployeeDetailArguments
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import com.itrocket.union.employees.presentation.view.EmployeeComposeFragmentDirections
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.manual.ParamDomain

interface EmployeeStore : Store<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnEmployeeClicked(val employeeId: String) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        data class OnFilterResult(val params: List<ParamDomain>) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val employees: List<EmployeeDomain> = listOf(),
        val isShowSearch: Boolean = false,
        val searchText: String = ""
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel

        data class ShowDetail(val employeeId: String) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = EmployeeComposeFragmentDirections.toEmployeeDetail(
                    EmployeeDetailArguments(employeeId = employeeId)
                )
        }

        data class ShowFilter(val filters: List<ParamDomain>) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = EmployeeComposeFragmentDirections.toFilter(
                    FilterArguments(
                        filters,
                        CatalogType.EMPLOYEES
                    )
                )
        }
    }
}