package com.itrocket.union.departments.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.departmentDetail.presentation.store.DepartmentDetailArguments
import com.itrocket.union.departments.domain.entity.DepartmentDomain
import com.itrocket.union.departments.presentation.view.DepartmentComposeFragmentDirections
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.manual.ParamDomain

interface DepartmentStore :
    Store<DepartmentStore.Intent, DepartmentStore.State, DepartmentStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        class OnFilterResult(val params: List<ParamDomain>) : Intent()
        data class OnDepartmentClicked(val id: String) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val departments: List<DepartmentDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DepartmentComposeFragmentDirections.toDepartmentDetail(
                    DepartmentDetailArguments(id = id)
                )
        }

        data class ShowFilter(val filters: List<ParamDomain>) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DepartmentComposeFragmentDirections.toFilter(FilterArguments(filters))
        }
    }
}