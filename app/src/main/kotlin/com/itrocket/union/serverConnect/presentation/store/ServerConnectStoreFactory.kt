package com.itrocket.union.serverConnect.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.network.HttpException
import com.itrocket.union.serverConnect.domain.ServerConnectInteractor
import com.itrocket.union.serverConnect.domain.StyleInteractor
import com.itrocket.union.theme.domain.ColorInteractor
import com.itrocket.union.theme.domain.MediaInteractor
import com.itrocket.union.theme.domain.entity.Medias
import com.itrocket.union.uniqueDeviceId.store.UniqueDeviceIdRepository
import com.squareup.moshi.JsonEncodingException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class ServerConnectStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val serverConnectInteractor: ServerConnectInteractor,
    private val initialState: ServerConnectStore.State?,
    private val colorInteractor: ColorInteractor,
    private val mediaInteractor: MediaInteractor,
    private val errorInteractor: ErrorInteractor,
    private val deviceIdRepository: UniqueDeviceIdRepository
) : KoinComponent {
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
        BaseExecutor<ServerConnectStore.Intent, Unit, ServerConnectStore.State, Result, ServerConnectStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ServerConnectStore.State
        ) {
            Timber.d("deviceId ${deviceIdRepository.getUniqueDeviceId()}")

            dispatch(Result.MediaList(mediaInteractor.getMedias()))
            val serverUrl = serverConnectInteractor.getBaseUrl()
            val port = serverConnectInteractor.getPort()
            if (!serverUrl.isNullOrBlank()) {
                dispatch(Result.ServerAddress(serverUrl))
            }
            if (!port.isNullOrBlank()) {
                dispatch(Result.Port(port))
            }
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
                ServerConnectStore.Intent.OnNextClicked -> onNextClicked(getState)
            }
        }

        private suspend fun onNextClicked(getState: () -> ServerConnectStore.State) {
            publish(ServerConnectStore.Label.ParentLoading(true))
            catchException {
                serverConnectInteractor.clearAllSyncDataIfNeeded(
                    getState().serverAddress,
                    getState().port
                )
                serverConnectInteractor.saveBaseUrl(getState().serverAddress)
                serverConnectInteractor.savePort(getState().port)
                loadSettings()
                publish(ServerConnectStore.Label.NextFinish)
            }
            publish(ServerConnectStore.Label.ParentLoading(false))
        }

        private suspend fun loadSettings() {
            try {
                val styleInteractor by inject<StyleInteractor>()
                val colors = styleInteractor.getStyleSettings()
                val logoFile = styleInteractor.getLogoFile()
                val headerFile = styleInteractor.getHeaderFile()
                colorInteractor.saveColorSettings(
                    mainColor = colors.mainColor,
                    mainTextColor = colors.mainTextColor,
                    secondaryTextColor = colors.secondaryTextColor,
                    appBarBackgroundColor = colors.appBarBackgroundColor,
                    appBarTextColor = colors.appBarTextColor
                )
                colorInteractor.initColorSettings()
                mediaInteractor.saveMedias(headerFile = headerFile, logoFile = logoFile)
            } catch (t: Throwable) {
                when (t) {
                    is JsonEncodingException -> throw errorInteractor.getThrowableByResId(R.string.common_server_validate_error)
                    is HttpException -> {
                        colorInteractor.saveLocalColorSettings()
                        colorInteractor.initColorSettings()
                        mediaInteractor.removeMedias()
                    }
                    else -> throw t
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(ServerConnectStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

    }

    private fun isEnabled(serverAddress: String, port: String) =
        serverConnectInteractor.validatePort(port) &&
                serverConnectInteractor.validateServerAddress(serverAddress)

    private sealed class Result {
        data class ServerAddress(val serverAddress: String) : Result()
        data class Port(val port: String) : Result()
        data class MediaList(val medias: Medias) : Result()
    }

    private object ReducerImpl : Reducer<ServerConnectStore.State, Result> {
        override fun ServerConnectStore.State.reduce(result: Result) =
            when (result) {
                is Result.ServerAddress -> copy(serverAddress = result.serverAddress)
                is Result.Port -> copy(port = result.port)
                is Result.MediaList -> copy(medias = result.medias)
            }
    }
}