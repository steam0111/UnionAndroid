package com.itrocket.union.employeeDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.employeeDetail.EmployeeDetailModule
import com.itrocket.union.employeeDetail.presentation.store.EmployeeDetailStore

class EmployeeDetailComposeFragment :
    BaseComposeFragment<EmployeeDetailStore.Intent, EmployeeDetailStore.State, EmployeeDetailStore.Label>(
        EmployeeDetailModule.EMPLOYEE_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<EmployeeDetailComposeFragmentArgs>()

    override fun renderState(
        state: EmployeeDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            EmployeeDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(EmployeeDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}