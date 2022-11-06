package com.itrocket.union.comment.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers

class CommentStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val arguments: CommentArguments
) {
    fun create(): CommentStore =
        object : CommentStore,
            Store<CommentStore.Intent, CommentStore.State, CommentStore.Label> by storeFactory.create(
                name = "CommentStore",
                initialState = CommentStore.State(text = arguments.comment),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<CommentStore.Intent, Unit, CommentStore.State, Result, CommentStore.Label> =
        CommentExecutor()

    private inner class CommentExecutor :
        BaseExecutor<CommentStore.Intent, Unit, CommentStore.State, Result, CommentStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> CommentStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: CommentStore.Intent,
            getState: () -> CommentStore.State
        ) {
            when (intent) {
                CommentStore.Intent.OnAcceptClicked -> publish(
                    CommentStore.Label.GoBack(
                        CommentResult(entityId = arguments.entityId, comment = getState().text)
                    )
                )
                is CommentStore.Intent.OnTextChanged -> dispatch(Result.Text(intent.text))
                CommentStore.Intent.OnCloseClicked -> publish(
                    CommentStore.Label.GoBack()
                )
            }
        }

        override fun handleError(throwable: Throwable) {

        }
    }

    private sealed class Result {
        data class Text(val text: String) : Result()
    }

    private object ReducerImpl : Reducer<CommentStore.State, Result> {
        override fun CommentStore.State.reduce(result: Result) =
            when (result) {
                is Result.Text -> copy(text = result.text)
            }
    }
}