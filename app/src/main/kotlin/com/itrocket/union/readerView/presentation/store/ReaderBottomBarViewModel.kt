package com.itrocket.union.readerView.presentation.store

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.itrocket.core.state.StateSaver
import kotlinx.coroutines.flow.Flow
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent

class ReaderBottomBarViewModel(
    private val store: Store<Unit, ReaderBottomBarStore.State, Unit>,
    scopeQualifier: Qualifier? = null,
    private val stateSaver: StateSaver<ReaderBottomBarStore.State>? = null
) : ViewModel() {
    private var scope: Scope? = null

    val state: Flow<ReaderBottomBarStore.State> = store.states

    init {
        scopeQualifier?.let { qualifier ->
            scope = KoinJavaComponent.getKoin().createScope(qualifier.value, qualifier)
        }
    }

    override fun onCleared() {
        stateSaver?.save(store.state)
        store.dispose()
        scope?.close()
        super.onCleared()
    }
}