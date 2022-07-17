package com.itrocket.union.documentCreate

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStore
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStoreFactory
import com.itrocket.union.documentCreate.presentation.view.DocumentCreateComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.documentCreate.domain.DocumentAccountingObjectManager
import com.itrocket.union.documentCreate.domain.DocumentReservesManager

object DocumentCreateModule {
    val DOCUMENTCREATE_VIEW_MODEL_QUALIFIER = named("DOCUMENTCREATE_VIEW_MODEL")

    val module = module {
        viewModel(DOCUMENTCREATE_VIEW_MODEL_QUALIFIER) { (args: DocumentCreateComposeFragmentArgs) ->
            BaseViewModel(get<DocumentCreateStore>() {
                parametersOf(args)
            })
        }

        factory {
            DocumentCreateInteractor(get(), get(), get())
        }

        factory {
            DocumentAccountingObjectManager(get(), get(), get())
        }

        factory {
            DocumentReservesManager(get(), get(), get(), get())
        }

        factory { (args: DocumentCreateComposeFragmentArgs) ->
            DocumentCreateStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.documentCreateArguments,
                get(),
                get(),
                get()
            ).create()
        }
    }
}