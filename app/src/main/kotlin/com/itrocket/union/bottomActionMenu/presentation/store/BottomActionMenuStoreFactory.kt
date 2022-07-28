package com.itrocket.union.bottomActionMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.entity.ObjectAction

class BottomActionMenuStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val bottomActionMenuArguments: AccountingObjectDomain
) {
    fun create(): BottomActionMenuStore =
        object : BottomActionMenuStore,
            Store<BottomActionMenuStore.Intent, BottomActionMenuStore.State, BottomActionMenuStore.Label> by storeFactory.create(
                name = "BottomActionMenuStore",
                initialState = BottomActionMenuStore.State(
                    types = ObjectAction.values().toList(),
                    item = bottomActionMenuArguments
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<BottomActionMenuStore.Intent, Unit, BottomActionMenuStore.State, Unit, BottomActionMenuStore.Label> =
        BottomActionMenuExecutor()

    private inner class BottomActionMenuExecutor :
        BaseExecutor<BottomActionMenuStore.Intent, Unit, BottomActionMenuStore.State, Unit, BottomActionMenuStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> BottomActionMenuStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: BottomActionMenuStore.Intent,
            getState: () -> BottomActionMenuStore.State
        ) {
            when (intent) {
                is BottomActionMenuStore.Intent.OnTypeClicked -> {
                    when (intent.type) {
                        ObjectAction.OPEN_CARD -> {
                            publish(BottomActionMenuStore.Label.ShowDetail(intent.item))
                        }
                        ObjectAction.DELETE_FROM_LIST -> {}
                        ObjectAction.CREATE_DOC -> {}
                    }
                    publish(
                        BottomActionMenuStore.Label.GoBack(
                            BottomActionMenuResult(intent.type)
                        )
                    )
                }
            }

//                BottomActionMenuStore.Intent.OnCreateDocClicked -> {
//                    Log.d("SukhanovTest", "Click CREATE ${bottomActionMenuArguments.bottomActionMenuDocument.title}")
//                }
//                is BottomActionMenuStore.Intent.OnOpenItemClicked -> {
//
//                }
//                is BottomActionMenuStore.Intent.OnDeleteItemClicked -> {
//
//                }
        }

        override fun handleError(throwable: Throwable) {
            publish(BottomActionMenuStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private object ReducerImpl : Reducer<BottomActionMenuStore.State, Unit> {
        override fun BottomActionMenuStore.State.reduce(result: Unit): BottomActionMenuStore.State =
            copy()
    }
}