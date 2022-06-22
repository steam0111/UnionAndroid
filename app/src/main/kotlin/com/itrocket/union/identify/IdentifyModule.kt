package com.itrocket.union.identify

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.accountingObjectDetail.data.AccountingObjectDetailRepositoryImpl
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailComposeFragmentArgs
import com.itrocket.union.accountingObjects.data.AccountingObjectRepositoryImpl
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.identify.data.IdentifyRepositoryImpl
import com.itrocket.union.identify.domain.IdentifyInteractor
import com.itrocket.union.identify.domain.dependencies.IdentifyRepository
import com.itrocket.union.identify.presentation.store.IdentifyStore
import com.itrocket.union.identify.presentation.store.IdentifyStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object IdentifyModule {
    val IDENTIFY_VIEW_MODEL_QUALIFIER = named("IDENTIFY_VIEW_MODEL")

    val module = module {
        viewModel(IDENTIFY_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<IdentifyStore>())
        }

        factory<IdentifyRepository> {
            IdentifyRepositoryImpl()
        }

        factory {
            IdentifyInteractor(get(), get())
        }

        factory {
            IdentifyStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}