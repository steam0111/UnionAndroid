package com.itrocket.union.newAccountingObject.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.newAccountingObject.domain.NewAccountingObjectInteractor
import com.itrocket.union.newAccountingObject.domain.entity.NewAccountingObjectDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor

class NewAccountingObjectStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val newAccountingObjectInteractor: NewAccountingObjectInteractor,
    private val newAccountingObjectArguments: NewAccountingObjectArguments
) {
    fun create(): NewAccountingObjectStore =
        object : NewAccountingObjectStore,
            Store<NewAccountingObjectStore.Intent, NewAccountingObjectStore.State, NewAccountingObjectStore.Label> by storeFactory.create(
                name = "NewAccountingObjectStore",
                initialState = NewAccountingObjectStore.State(accountingObject = newAccountingObjectArguments.accountingObject),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<NewAccountingObjectStore.Intent, Unit, NewAccountingObjectStore.State, Unit, NewAccountingObjectStore.Label> =
        NewAccountingObjectExecutor()

    private inner class NewAccountingObjectExecutor :
        BaseExecutor<NewAccountingObjectStore.Intent, Unit, NewAccountingObjectStore.State, Unit, NewAccountingObjectStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> NewAccountingObjectStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: NewAccountingObjectStore.Intent,
            getState: () -> NewAccountingObjectStore.State
        ) {
            when (intent) {
                NewAccountingObjectStore.Intent.OnCrossClicked -> publish(NewAccountingObjectStore.Label.GoBack())
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(NewAccountingObjectStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private object ReducerImpl : Reducer<NewAccountingObjectStore.State, Unit> {
        override fun NewAccountingObjectStore.State.reduce(result: Unit) = copy()
    }
}