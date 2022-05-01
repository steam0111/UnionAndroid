package ${packageName}.${featurePackageName}.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import ${packageName}.${featurePackageName}.domain.${featureName}Interactor
import ${packageName}.${featurePackageName}.domain.entity.${featureName}Domain
import ${packageName}.core.CoreDispatchers

class ${featureName}StoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val ${featureName?uncap_first}Interactor: ${featureName}Interactor,
    private val ${featureName?uncap_first}Arguments: ${featureName}Arguments
) {
    fun create(): ${featureName}Store =
        object : ${featureName}Store,
            Store<${featureName}Store.Intent, ${featureName}Store.State, ${featureName}Store.Label> by storeFactory.create(
                name = "${featureName}Store",
                initialState = ${featureName}Store.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<${featureName}Store.Intent, Unit, ${featureName}Store.State, Result, ${featureName}Store.Label> =
        ${featureName}Executor()

    private inner class ${featureName}Executor :
        SuspendExecutor<${featureName}Store.Intent, Unit, ${featureName}Store.State, Result, ${featureName}Store.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ${featureName}Store.State
        ) {
        }

        override suspend fun executeIntent(
            intent: ${featureName}Store.Intent,
            getState: () -> ${featureName}Store.State
        ) {
            when (intent) {
                 ${featureName}Store.Intent.OnBackClicked -> publish(${featureName}Store.Label.GoBack)
            }
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<${featureName}Store.State, Result> {
        override fun ${featureName}Store.State.reduce(result: Result) =
            when (result) {
                  is Result.Loading -> copy(isLoading = result.isLoading)
            }
    }
}