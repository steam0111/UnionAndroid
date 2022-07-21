package com.itrocket.union.authContainer

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.core.state.InMemoryStateSaver
import com.itrocket.core.state.StateSaver
import com.itrocket.union.authContainer.data.AuthContainerRepositoryImpl
import com.itrocket.union.authContainer.domain.AuthContainerInteractor
import com.itrocket.union.authContainer.domain.dependencies.AuthContainerRepository
import com.itrocket.union.authContainer.presentation.store.AuthContainerStore
import com.itrocket.union.authContainer.presentation.store.AuthContainerStoreFactory
import com.itrocket.union.authContainer.presentation.view.AuthContainerArguments
import com.itrocket.union.authContainer.presentation.view.AuthContainerComposeFragmentArgs
import com.itrocket.union.authUser.AuthUserModule.AUTHUSER_STATE_SAVER
import com.itrocket.union.authUser.presentation.store.AuthUserStore
import com.itrocket.union.serverConnect.ServerConnectModule.SERVERCONNECT_STATE_SAVER
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AuthContainerModule {
    val AUTH_VIEW_MODEL_QUALIFIER = named("AUTH_VIEW_MODEL")

    val module = module {
        viewModel(AUTH_VIEW_MODEL_QUALIFIER) { (args: AuthContainerComposeFragmentArgs) ->
            BaseViewModel(
                get<AuthContainerStore>() {
                    parametersOf(args)
                },
                scopeQualifier = named<AuthContainerModule>()
            )
        }

        factory<AuthContainerRepository> {
            AuthContainerRepositoryImpl()
        }

        factory {
            AuthContainerInteractor(get(), get())
        }

        factory { (args: AuthContainerComposeFragmentArgs) ->
            AuthContainerStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.authContainerArguments ?: AuthContainerArguments(isShowBackButton = false),
            ).create()
        }

        scope<AuthContainerModule> {
            scoped<StateSaver<ServerConnectStore.State>>(SERVERCONNECT_STATE_SAVER) {
                InMemoryStateSaver()
            }
            scoped<StateSaver<AuthUserStore.State>>(AUTHUSER_STATE_SAVER) {
                InMemoryStateSaver()
            }
        }
    }
}