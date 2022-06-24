package com.itrocket.union.newAccountingObject.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.AccountingObjectDetailInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

class NewAccountingObjectStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val accountingObjectDetailInteractor: AccountingObjectDetailInteractor,
    private val newAccountingObjectArguments: NewAccountingObjectArguments
) {
    fun create(): NewAccountingObjectStore =
        object : NewAccountingObjectStore,
            Store<NewAccountingObjectStore.Intent, NewAccountingObjectStore.State, NewAccountingObjectStore.Label> by storeFactory.create(
                name = "NewAccountingObjectStore",
                initialState = NewAccountingObjectStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<NewAccountingObjectStore.Intent, Unit, NewAccountingObjectStore.State, Result, NewAccountingObjectStore.Label> =
        NewAccountingObjectExecutor()

    private inner class NewAccountingObjectExecutor :
        BaseExecutor<NewAccountingObjectStore.Intent, Unit, NewAccountingObjectStore.State, Result, NewAccountingObjectStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> NewAccountingObjectStore.State
        ) {
            dispatch(Result.Loading(true))
            catchException {
                val accountingObject = accountingObjectDetailInteractor.getAccountingObject(
                    newAccountingObjectArguments.accountingObjectId
                )
                dispatch(Result.AccountingObject(accountingObject))
            }
            dispatch(Result.Loading(false))
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

    private sealed class Result {
        data class Loading(val loading: Boolean) : Result()
        data class AccountingObject(val accountingObject: AccountingObjectDomain) : Result()
    }

    private object ReducerImpl : Reducer<NewAccountingObjectStore.State, Result> {
        override fun NewAccountingObjectStore.State.reduce(result: Result) = when (result) {
            is Result.AccountingObject -> copy(accountingObject = result.accountingObject)
            is Result.Loading -> copy(isLoading = result.loading)
        }
    }
}