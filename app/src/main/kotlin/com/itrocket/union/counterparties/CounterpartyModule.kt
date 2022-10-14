package com.itrocket.union.counterparties

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.counterparties.data.CounterpartyRepositoryImpl
import com.itrocket.union.counterparties.domain.CounterpartyInteractor
import com.itrocket.union.counterparties.domain.dependencies.CounterpartyRepository
import com.itrocket.union.counterparties.presentation.store.CounterpartyStore
import com.itrocket.union.counterparties.presentation.store.CounterpartyStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object CounterpartyModule {
    val COUNTERPARTY_VIEW_MODEL_QUALIFIER = named("COUNTERPARTY_VIEW_MODEL")

    val module = module {
        viewModel(COUNTERPARTY_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<CounterpartyStore>())
        }

        factory<CounterpartyRepository> {
            CounterpartyRepositoryImpl(get(), get())
        }

        factory {
            CounterpartyInteractor(get(), get())
        }

        factory {
            CounterpartyStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}