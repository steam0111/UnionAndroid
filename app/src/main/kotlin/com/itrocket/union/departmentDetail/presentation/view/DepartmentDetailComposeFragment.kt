package com.itrocket.union.departmentDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.departmentDetail.DepartmentDetailModule
import com.itrocket.union.departmentDetail.presentation.store.DepartmentDetailStore

class DepartmentDetailComposeFragment :
    BaseComposeFragment<DepartmentDetailStore.Intent, DepartmentDetailStore.State, DepartmentDetailStore.Label>(
        DepartmentDetailModule.DEPARTMENT_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<DepartmentDetailComposeFragmentArgs>()

    override fun renderState(
        state: DepartmentDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            DepartmentDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(DepartmentDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}