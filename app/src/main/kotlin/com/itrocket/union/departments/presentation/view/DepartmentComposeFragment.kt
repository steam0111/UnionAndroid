package com.itrocket.union.departments.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.departments.DepartmentModule.DEPARTMENT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.departments.presentation.store.DepartmentStore
import com.itrocket.union.filter.presentation.view.FilterComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult

class DepartmentComposeFragment :
    BaseComposeFragment<DepartmentStore.Intent, DepartmentStore.State, DepartmentStore.Label>(
        DEPARTMENT_VIEW_MODEL_QUALIFIER
    ) {

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = FilterComposeFragment.FILTER_RESULT_CODE,
                resultLabel = FilterComposeFragment.FILTER_RESULT_LABEL,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(DepartmentStore.Intent.OnFilterResult(it))
                    }
                }
            )
        )

    override fun renderState(
        state: DepartmentStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            DepartmentScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = { accept(DepartmentStore.Intent.OnBackClicked) },
                onItemClickListener = { accept(DepartmentStore.Intent.OnDepartmentClicked(it.id)) },
                onSearchTextChanged = {
                    accept(DepartmentStore.Intent.OnSearchTextChanged(it))
                },
                onSearchClickListener = {
                    accept(DepartmentStore.Intent.OnSearchClicked)
                },
                onFilterClickListener = { accept(DepartmentStore.Intent.OnFilterClicked) }
            )
        }
    }
}