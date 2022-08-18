package com.itrocket.union.documents

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.documents.data.DocumentRepositoryImpl
import com.itrocket.union.documents.domain.DocumentInteractor
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.presentation.store.DocumentStore
import com.itrocket.union.documents.presentation.store.DocumentStoreFactory
import com.itrocket.union.documents.presentation.view.DocumentComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DocumentModule {
    val DOCUMENT_VIEW_MODEL_QUALIFIER = named("DOCUMENT_VIEW_MODEL")

    val module = module {
        viewModel(DOCUMENT_VIEW_MODEL_QUALIFIER) { (args: DocumentComposeFragmentArgs) ->
            BaseViewModel(get<DocumentStore> {
                parametersOf(args)
            })
        }

        factory<DocumentRepository> {
            DocumentRepositoryImpl(get(), get(), get())
        }

        factory {
            DocumentInteractor(get(), get())
        }

        factory { (args: DocumentComposeFragmentArgs) ->
            DocumentStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.documentComposeFragmentArgs,
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}