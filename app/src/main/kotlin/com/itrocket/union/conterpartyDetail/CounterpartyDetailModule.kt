package com.itrocket.union.conterpartyDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.conterpartyDetail.data.CounterpartyDetailRepositoryImpl
import com.itrocket.union.conterpartyDetail.domain.CounterpartyDetailInteractor
import com.itrocket.union.conterpartyDetail.domain.dependencies.CounterpartyDetailRepository
import com.itrocket.union.conterpartyDetail.presentation.store.CounterpartyDetailStore
import com.itrocket.union.conterpartyDetail.presentation.store.CounterpartyDetailStoreFactory
import com.itrocket.union.conterpartyDetail.presentation.view.CounterpartyDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object CounterpartyDetailModule {
    val COUNTERPARTY_DETAIL_VIEW_MODEL_QUALIFIER = named("COUNTERPARTY_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(COUNTERPARTY_DETAIL_VIEW_MODEL_QUALIFIER) { (args: CounterpartyDetailComposeFragmentArgs) ->
            BaseViewModel(get<CounterpartyDetailStore>() {
                parametersOf(args)
            })
        }

        factory<CounterpartyDetailRepository> {
            CounterpartyDetailRepositoryImpl(get())
        }

        factory {
            CounterpartyDetailInteractor(get(), get())
        }

        factory { (args: CounterpartyDetailComposeFragmentArgs) ->
            CounterpartyDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.counterpartyDetailComposeFragmentArgs
            ).create()
        }
    }
}