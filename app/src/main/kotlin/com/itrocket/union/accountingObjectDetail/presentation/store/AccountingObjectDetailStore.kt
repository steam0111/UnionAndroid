package com.itrocket.union.accountingObjectDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

interface AccountingObjectDetailStore :
    Store<AccountingObjectDetailStore.Intent, AccountingObjectDetailStore.State, AccountingObjectDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnCheckedFullCharacteristics(val isChecked: Boolean) : Intent()
        data class OnPageSelected(val selectedPage: Int) : Intent()
        object OnReadingModeClicked : Intent()
        object OnDocumentSearchClicked : Intent()
        object OnDocumentAddClicked : Intent()
    }

    data class State(
        val accountingObjectDomain: AccountingObjectDomain,
        val isLoading: Boolean = false,
        val isFullCharacteristicChecked: Boolean = false,
        val selectedPage: Int = 0
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
    }
}