package com.itrocket.union.newAccountingObject.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.itrocket.union.newAccountingObject.NewAccountingObjectModule.NEWACCOUNTINGOBJECT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R

class NewAccountingObjectComposeFragment :
    BaseComposeBottomSheet<NewAccountingObjectStore.Intent, NewAccountingObjectStore.State, NewAccountingObjectStore.Label>(
        NEWACCOUNTINGOBJECT_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun renderState(
        state: NewAccountingObjectStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            NewAccountingObjectScreen(
                state = state,
                appInsets = appInsets,
                onCrossClickListener = {
                    accept(NewAccountingObjectStore.Intent.OnCrossClicked)
                }
            )
        }
    }

    companion object {
        const val NEW_ACCOUNTING_OBJECT_ARGUMENT = "new accounting object argument"
        const val NEW_ACCOUNTING_OBJECT_RESULT = "new accounting object result"
        const val NEW_ACCOUNTING_OBJECT_RESULT_CODE = "new accounting object result code"
    }
}