package com.itrocket.union.bottomActionMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.Store

interface BottomActionMenuStore :
    Store<BottomActionMenuStore.Intent,
            BottomActionMenuStore.State,
            BottomActionMenuStore.Label> {
    sealed class Intent {
        object OnCreateDocClicked : Intent()
        object OnOpenItemClicked : Intent()
        object OnDeleteItemClicked : Intent()
    }

    data class State(
        val isEnabled: Boolean = true
    )

    sealed class Label
}