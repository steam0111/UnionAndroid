package com.itrocket.union.splash.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.container.domain.IsDbSyncedUseCase
import com.itrocket.union.container.domain.IsUserAuthorizedUseCase
import com.itrocket.union.container.presentation.InitialScreen
import com.itrocket.union.splash.SplashInteractor
import com.itrocket.union.theme.domain.ColorInteractor
import com.itrocket.union.theme.domain.MediaInteractor
import com.itrocket.union.theme.domain.entity.Medias
import kotlinx.coroutines.delay

class SplashStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val mediaInteractor: MediaInteractor,
    private val colorInteractor: ColorInteractor,
    private val splashInteractor: SplashInteractor
) {
    fun create(): SplashStore =
        object : SplashStore,
            Store<SplashStore.Intent, SplashStore.State, SplashStore.Label> by storeFactory.create(
                name = "SplashStore",
                initialState = SplashStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<SplashStore.Intent, Unit, SplashStore.State, Result, SplashStore.Label> =
        SplashExecutor()

    private inner class SplashExecutor :
        BaseExecutor<SplashStore.Intent, Unit, SplashStore.State, Result, SplashStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> SplashStore.State
        ) {
            loadSettings()
            delay(SPLASH_DELAY)
            showInitialScreen()
        }

        override suspend fun executeIntent(
            intent: SplashStore.Intent,
            getState: () -> SplashStore.State
        ) {

        }

        override fun handleError(throwable: Throwable) {

        }

        private suspend fun loadSettings() {
            colorInteractor.initColorSettings()
            val medias = mediaInteractor.getMedias()
            dispatch(Result.Media(medias))
        }

        private suspend fun showInitialScreen() {
            publish(
                when (splashInteractor.getInitialScreen()) {
                    InitialScreen.AUTH -> SplashStore.Label.ShowAuth
                    InitialScreen.DOCUMENTS_MENU -> SplashStore.Label.ShowDocumentsMenu
                    InitialScreen.SYNC_ALL -> SplashStore.Label.ShowSyncAll
                }
            )
        }
    }

    private sealed class Result {
        data class Media(val medias: Medias) : Result()
    }

    private object ReducerImpl : Reducer<SplashStore.State, Result> {
        override fun SplashStore.State.reduce(result: Result) = when (result) {
            is Result.Media -> copy(medias = result.medias)
        }
    }

    companion object {
        private const val SPLASH_DELAY = 1500L
    }
}