package com.itrocket.union.newAccountingObject.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.newAccountingObject.presentation.view.NewAccountingObjectComposeFragment

interface NewAccountingObjectStore :
    Store<NewAccountingObjectStore.Intent, NewAccountingObjectStore.State, NewAccountingObjectStore.Label> {

    sealed class Intent {
        object OnCrossClicked : Intent()
    }

    data class State(
        val accountingObject: AccountingObjectDomain
    )

    sealed class Label {
        data class GoBack(override val result: NewAccountingObjectResult? = null) : Label(),
            GoBackDialogNavigationLabel {
            override val resultCode: String
                get() = NewAccountingObjectComposeFragment.NEW_ACCOUNTING_OBJECT_RESULT_CODE

            override val resultLabel: String
                get() = NewAccountingObjectComposeFragment.NEW_ACCOUNTING_OBJECT_RESULT
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}