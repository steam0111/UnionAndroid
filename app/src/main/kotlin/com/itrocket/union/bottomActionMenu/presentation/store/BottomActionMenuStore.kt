package com.itrocket.union.bottomActionMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface BottomActionMenuStore :
    Store<BottomActionMenuStore.Intent,
            BottomActionMenuStore.State,
            BottomActionMenuStore.Label> {
    sealed class Intent {
        object OnCreateDocClicked : Intent()
        class OnOpenItemClicked(val item: ReservesDomain) : Intent()
        class OnDeleteItemClicked(val item: ReservesDomain) : Intent()
    }

    data class State(
        val isEnabled: Boolean = true
    )

    sealed class Label
}