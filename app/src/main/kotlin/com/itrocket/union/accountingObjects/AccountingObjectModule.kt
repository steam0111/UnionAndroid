package com.itrocket.union.accountingObjects

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.accountingObjects.data.AccountingObjectRepositoryImpl
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectArguments
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStoreFactory
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AccountingObjectModule {
    val ACCOUNTING_OBJECT_VIEW_MODEL_QUALIFIER = named("ACCOUNTING_OBJECT_VIEW_MODEL")

    val module = module {
        viewModel(ACCOUNTING_OBJECT_VIEW_MODEL_QUALIFIER) { (arg: AccountingObjectComposeFragmentArgs) ->
            BaseViewModel(get<AccountingObjectStore>() {
                parametersOf(arg)
            })
        }

        factory<AccountingObjectRepository> {
            AccountingObjectRepositoryImpl(get(), get())
        }

        factory {
            AccountingObjectInteractor(get(), get())
        }

        factory { (arg: AccountingObjectComposeFragmentArgs) ->
            AccountingObjectStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                arg.accountingObjectArguments,
                get()
            ).create()
        }
    }
}