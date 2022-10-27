package com.itrocket.union.syncAll

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.syncAll.data.SyncAllRepositoryImpl
import com.itrocket.union.syncAll.domain.SyncAllInteractor
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import com.itrocket.union.syncAll.presentation.store.SyncAllStore
import com.itrocket.union.syncAll.presentation.store.SyncAllStoreFactory
import com.itrocket.union.syncAll.presentation.view.SyncAllComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SyncAllModule {
    val SYNCALL_VIEW_MODEL_QUALIFIER = named("SYNCALL_VIEW_MODEL")

    val module = module {
        viewModel(SYNCALL_VIEW_MODEL_QUALIFIER) { (args: SyncAllComposeFragmentArgs) ->
            BaseViewModel(get<SyncAllStore>() {
                parametersOf(args)
            })
        }

        factory<SyncAllRepository> {
            SyncAllRepositoryImpl(
                get(),
                get(),
                get(),
                get()
            )
        }

        factory {
            SyncAllInteractor(get(), get(), get())
        }

        factory { (args: SyncAllComposeFragmentArgs) ->
            SyncAllStoreFactory(
                DefaultStoreFactory,
                get(),
                args.syncAllComposeFragmentArgs,
                get(),
                get(),
            ).create()
        }
    }
}