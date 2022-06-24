package com.itrocket.union.departments.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.departments.DepartmentModule.DEPARTMENT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.departments.presentation.store.DepartmentStore

class DepartmentComposeFragment :
    BaseComposeFragment<DepartmentStore.Intent, DepartmentStore.State, DepartmentStore.Label>(
        DEPARTMENT_VIEW_MODEL_QUALIFIER
    ) {
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
                onItemClickListener = { accept(DepartmentStore.Intent.OnDepartmentClicked(it.id)) }
            )
        }
    }
}