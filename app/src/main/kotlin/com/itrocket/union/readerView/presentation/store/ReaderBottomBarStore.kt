package com.itrocket.union.readerView.presentation.store

import com.arkivanov.mvikotlin.core.store.Store

interface ReaderBottomBarStore :
    Store<Unit, ReaderBottomBarStore.State, Unit> {

    data class State(val readerPower: Int? = null)
}