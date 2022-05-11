package com.itrocket.union.authAndLicense.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.authAndLicense.domain.AuthAndLicenseInteractor
import com.itrocket.union.authAndLicense.domain.entity.AuthAndLicenseDomain
import com.itrocket.core.base.CoreDispatchers

class AuthAndLicenseStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val authAndLicenseInteractor: AuthAndLicenseInteractor
) {
    fun create(): AuthAndLicenseStore =
        object : AuthAndLicenseStore,
            Store<AuthAndLicenseStore.Intent, AuthAndLicenseStore.State, AuthAndLicenseStore.Label> by storeFactory.create(
                name = "AuthAndLicenseStore",
                initialState = AuthAndLicenseStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<AuthAndLicenseStore.Intent, Unit, AuthAndLicenseStore.State, Result, AuthAndLicenseStore.Label> =
        AuthAndLicenseExecutor()

    private inner class AuthAndLicenseExecutor :
        SuspendExecutor<AuthAndLicenseStore.Intent, Unit, AuthAndLicenseStore.State, Result, AuthAndLicenseStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AuthAndLicenseStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: AuthAndLicenseStore.Intent,
            getState: () -> AuthAndLicenseStore.State
        ) {
            when (intent) {
                is AuthAndLicenseStore.Intent.OnClientCodeChanged -> dispatch(Result.ClientCode(intent.clientCode))
                is AuthAndLicenseStore.Intent.OnNameDeviceChanged -> dispatch(Result.DeviceName(intent.deviceName))
                is AuthAndLicenseStore.Intent.OnSecurityCodeChanged -> dispatch(Result.SecurityCode(intent.securityCode))
            }
        }
    }

    private sealed class Result {
        data class ClientCode(val clientCode: String) : Result()
        data class DeviceName(val deviceName: String) : Result()
        data class SecurityCode(val securityCode: String) : Result()
    }

    private object ReducerImpl : Reducer<AuthAndLicenseStore.State, Result> {
        override fun AuthAndLicenseStore.State.reduce(result: Result) =
            when (result) {
                is Result.ClientCode -> copy(clientCode = result.clientCode)
                is Result.DeviceName -> copy(deviceName = result.deviceName)
                is Result.SecurityCode -> copy(securityCode = result.securityCode)
            }
    }
}