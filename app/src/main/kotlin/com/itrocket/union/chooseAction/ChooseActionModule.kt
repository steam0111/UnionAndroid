package com.itrocket.union.chooseAction

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.chooseAction.presentation.store.ChooseActionStore
import com.itrocket.union.chooseAction.presentation.store.ChooseActionStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ChooseActionModule {
    val CHOOSEACTION_VIEW_MODEL_QUALIFIER = named("CHOOSEACTION_VIEW_MODEL")

    val module = module {
        viewModel(CHOOSEACTION_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ChooseActionStore>())
        }

        factory {
            ChooseActionStoreFactory(
                DefaultStoreFactory,
                get(),
            ).create()
        }
    }
}