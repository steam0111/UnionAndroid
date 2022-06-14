package com.itrocket.union.employees.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.employees.EmployeeModule.EMPLOYEE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.employees.presentation.store.EmployeeStore

class EmployeeComposeFragment :
    BaseComposeFragment<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label>(
        EMPLOYEE_VIEW_MODEL_QUALIFIER
    ) {
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
            )
        }
    }
}