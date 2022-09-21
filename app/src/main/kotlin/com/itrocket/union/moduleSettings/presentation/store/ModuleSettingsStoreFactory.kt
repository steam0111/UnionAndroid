package com.itrocket.union.moduleSettings.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.moduleSettings.domain.ModuleSettingsInteractor
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModuleSettingsStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val moduleSettingsInteractor: ModuleSettingsInteractor,
    private val errorInteractor: ErrorInteractor,
) {
    fun create(): ModuleSettingsStore =
        object : ModuleSettingsStore,
            Store<ModuleSettingsStore.Intent, ModuleSettingsStore.State, ModuleSettingsStore.Label> by storeFactory.create(
                name = "ModuleSettingsStore",
                initialState = ModuleSettingsStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ModuleSettingsStore.Intent, Unit, ModuleSettingsStore.State, Result, ModuleSettingsStore.Label> =
        ModuleSettingsExecutor()

    private inner class ModuleSettingsExecutor :
        BaseExecutor<ModuleSettingsStore.Intent, Unit, ModuleSettingsStore.State, Result, ModuleSettingsStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ModuleSettingsStore.State
        ) {
            withContext(Dispatchers.Main) {
                moduleSettingsInteractor.checkInstalledService()
            }
            dispatch(
                Result.KeyCode(
                    moduleSettingsInteractor.getKeyCode() ?: 0
                )
            )
        }

        override suspend fun executeIntent(
            intent: ModuleSettingsStore.Intent,
            getState: () -> ModuleSettingsStore.State
        ) {
            when (intent) {
                ModuleSettingsStore.Intent.OnBackClicked -> publish(ModuleSettingsStore.Label.GoBack)
                is ModuleSettingsStore.Intent.OnCursorDefined -> {
                    dispatch(Result.KeyCode(intent.keyCode))
                    moduleSettingsInteractor.changeWaitingKeyCode(true)
                    dispatch(Result.WaitDefine(false))
                }
                ModuleSettingsStore.Intent.OnDefineCursorClicked -> {
                    moduleSettingsInteractor.changeWaitingKeyCode(true)
                    dispatch(Result.WaitDefine(true))
                }
                ModuleSettingsStore.Intent.OnSaveClicked -> {
                    moduleSettingsInteractor.applyChanges(
                        defaultService = getState().defaultService,
                        keyCode = getState().keyCode,
                        readerPower = getState().readerPower
                    )
                    publish(ModuleSettingsStore.Label.GoBack)
                }
                is ModuleSettingsStore.Intent.OnServicesHandled -> onServicesHandled(
                    services = intent.services,
                    defaultService = getState().defaultService
                )
                ModuleSettingsStore.Intent.OnDropdownDismiss -> dispatch(
                    Result.DropdownExpanded(
                        false
                    )
                )
                is ModuleSettingsStore.Intent.OnDropdownItemClicked -> {
                    dispatch(Result.DropdownExpanded(false))
                    dispatch(Result.Service(intent.service))
                }
                ModuleSettingsStore.Intent.OnDropdownOpenClicked -> dispatch(
                    Result.DropdownExpanded(
                        true
                    )
                )
                is ModuleSettingsStore.Intent.OnDefaultServiceHandled -> dispatch(
                    Result.Service(
                        intent.service
                    )
                )
            }
        }

        private fun onServicesHandled(services: List<String>, defaultService: String) {
            dispatch(
                Result.Services(services)
            )
            if (defaultService.isEmpty()) {
                val selectedService = services.firstOrNull() ?: DEFAULT_SERVICE
                dispatch(Result.Service(selectedService))
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(ModuleSettingsStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class KeyCode(val keyCode: Int) : Result()
        data class WaitDefine(val waitDefine: Boolean) : Result()
        data class Service(val service: String) : Result()
        data class Services(val services: List<String>) : Result()
        data class DropdownExpanded(val dropdownExpanded: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<ModuleSettingsStore.State, Result> {
        override fun ModuleSettingsStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.KeyCode -> copy(keyCode = result.keyCode)
                is Result.WaitDefine -> copy(isDefineWait = result.waitDefine)
                is Result.Service -> copy(defaultService = result.service)
                is Result.Services -> copy(services = result.services)
                is Result.DropdownExpanded -> copy(dropdownExpanded = result.dropdownExpanded)
            }
    }

    companion object {
        private const val DEFAULT_SERVICE = "ru.interid.chainwayservice"
    }
}