package com.itrocket.union.employees.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.employees.EmployeeModule.EMPLOYEE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.employees.presentation.store.EmployeeStore
import com.itrocket.union.filter.presentation.view.FilterComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult

class EmployeeComposeFragment :
    BaseComposeFragment<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label>(
        EMPLOYEE_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(EmployeeStore.Intent.OnBackClicked)
            }
        }
    }

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = FilterComposeFragment.FILTER_RESULT_CODE,
                resultLabel = FilterComposeFragment.FILTER_RESULT_LABEL,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(EmployeeStore.Intent.OnFilterResult(it))
                    }
                }
            )
        )

    override fun renderState(
        state: EmployeeStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            EmployeesScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = { accept(EmployeeStore.Intent.OnBackClicked) },
                onEmployeeClickListener = { accept(EmployeeStore.Intent.OnEmployeeClicked(it.id)) },
                onSearchTextChanged = {
                    accept(EmployeeStore.Intent.OnSearchTextChanged(it))
                },
                onSearchClickListener = {
                    accept(EmployeeStore.Intent.OnSearchClicked)
                },
                onFilterClickListener = {
                    accept(EmployeeStore.Intent.OnFilterClicked)
                },
                onLoadNext = {
                    accept(EmployeeStore.Intent.OnLoadNext)
                }
            )
        }
    }
}