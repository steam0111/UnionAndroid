package com.itrocket.union.serverConnect.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.serverConnect.domain.ServerConnectInteractor

class ServerConnectStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val serverConnectInteractor: ServerConnectInteractor,
    private val initialState: ServerConnectStore.State?
) {
    fun create(): ServerConnectStore =
        object : ServerConnectStore,
            Store<ServerConnectStore.Intent, ServerConnectStore.State, ServerConnectStore.Label> by storeFactory.create(
                name = "ServerConnectStore",
                initialState = initialState ?: ServerConnectStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ServerConnectStore.Intent, Unit, ServerConnectStore.State, Result, ServerConnectStore.Label> =
        ServerConnectExecutor()

    private inner class ServerConnectExecutor :
        SuspendExecutor<ServerConnectStore.Intent, Unit, ServerConnectStore.State, Result, ServerConnectStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ServerConnectStore.State
        ) {
            publish(
                ServerConnectStore.Label.ChangeEnable(
                    isEnabled(
                        getState().serverAddress,
                        getState().port
                    )
                )
            )
        }

        override suspend fun executeIntent(
            intent: ServerConnectStore.Intent,
            getState: () -> ServerConnectStore.State
        ) {
            when (intent) {
                is ServerConnectStore.Intent.OnServerAddressChanged -> {
                    dispatch(
                        Result.ServerAddress(
                            intent.serverAddress
                        )
                    )
                    publish(
                        ServerConnectStore.Label.ChangeEnable(
                            isEnabled(
                                getState().serverAddress,
                                getState().port
                            )
                        )
                    )
                }
                is ServerConnectStore.Intent.OnPortChanged -> {
                    if (intent.port.length <= ServerConnectInteractor.MAX_PORT_LENGTH) {
                        dispatch(Result.Port(intent.port))
                        publish(
                            ServerConnectStore.Label.ChangeEnable(
                                isEnabled(
                                    getState().serverAddress,
                                    getState().port
                                )
                            )
                        )
                    }
                }
                ServerConnectStore.Intent.OnNextClicked -> {
                    serverConnectInteractor.clearAllSyncDataIfNeeded(getState().serverAddress, getState().port)
                    serverConnectInteractor.saveBaseUrl(getState().serverAddress)
                    serverConnectInteractor.savePort(getState().port)
                    publish(ServerConnectStore.Label.NextFinish)
                }
            }
        }
    }

    private fun isEnabled(serverAddress: String, port: String) =
        serverConnectInteractor.validatePort(port) &&
                serverConnectInteractor.validateServerAddress(serverAddress)

    private sealed class Result {
        data class ServerAddress(val serverAddress: String) : Result()
        data class Port(val port: String) : Result()
    }

    private object ReducerImpl : Reducer<ServerConnectStore.State, Result> {
        override fun ServerConnectStore.State.reduce(result: Result) =
            when (result) {
                is Result.ServerAddress -> copy(serverAddress = result.serverAddress)
                is Result.Port -> copy(port = result.port)
            }
    }
}