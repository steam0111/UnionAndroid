package com.itrocket.union.authMain

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.authMain.data.AuthMainRepositoryImpl
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.presentation.store.AuthMainStore
import com.itrocket.union.authMain.presentation.store.AuthMainStoreFactory
import com.itrocket.union.authMain.presentation.view.AuthMainComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel

object AuthMainModule {
    val AUTHMAIN_VIEW_MODEL_QUALIFIER = named("AUTHMAIN_VIEW_MODEL")

    val module = module {
        viewModel(AUTHMAIN_VIEW_MODEL_QUALIFIER) { (args: AuthMainComposeFragmentArgs) ->
            BaseViewModel(get<AuthMainStore>() {
                parametersOf(args)
            })
        }

        factory<AuthMainRepository> {
            AuthMainRepositoryImpl()
        }

        factory {
            AuthMainInteractor(get(), get())
        }

        factory { (args: AuthMainComposeFragmentArgs) ->
            AuthMainStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.authMainComposeFragmentArgs
            ).create()
        }
    }
}