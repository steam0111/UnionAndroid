package com.itrocket.union.authUser

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.core.utils.getExternalStateSaver
import com.itrocket.core.utils.getSavedState
import com.itrocket.union.authContainer.AuthContainerModule
import com.itrocket.union.authUser.data.AuthUserRepositoryImpl
import com.itrocket.union.authUser.domain.AuthUserInteractor
import com.itrocket.union.authUser.domain.dependencies.AuthUserRepository
import com.itrocket.union.authUser.presentation.store.AuthUserStore
import com.itrocket.union.authUser.presentation.store.AuthUserStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AuthUserModule {
    val AUTHUSER_VIEW_MODEL_QUALIFIER = named("AUTHUSER_VIEW_MODEL")
    val AUTHUSER_STATE_SAVER = named("AUTHUSER_VIEW_MODEL")

    val module = module {
        viewModel(AUTHUSER_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(
                store = get<AuthUserStore>(),
                stateSaver = getExternalStateSaver<AuthUserStore.State, AuthContainerModule>(
                    AUTHUSER_STATE_SAVER
                )
            )
        }

        factory<AuthUserRepository> {
            AuthUserRepositoryImpl()
        }

        factory {
            AuthUserInteractor(get(), get())
        }

        factory {
            AuthUserStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                initialState = getSavedState<AuthUserStore.State, AuthContainerModule>(
                    AUTHUSER_STATE_SAVER
                ),
                get(),
                get()
            ).create()
        }
    }
}