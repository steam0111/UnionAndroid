package com.itrocket.union.accountingObjectDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.itrocket.union.accountingObjectDetail.data.AccountingObjectDetailRepositoryImpl
import com.itrocket.union.accountingObjectDetail.domain.AccountingObjectDetailInteractor
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStore
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStoreFactory
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailComposeFragmentArgs

object AccountingObjectDetailModule {
    val ACCOUNTINGOBJECTDETAIL_VIEW_MODEL_QUALIFIER = named("ACCOUNTINGOBJECTDETAIL_VIEW_MODEL")

    val module = module {
        viewModel(ACCOUNTINGOBJECTDETAIL_VIEW_MODEL_QUALIFIER) { (args: AccountingObjectDetailComposeFragmentArgs) ->
            BaseViewModel(get<AccountingObjectDetailStore>() {
                parametersOf(args)
            })
        }

        factory<AccountingObjectDetailRepository> {
            AccountingObjectDetailRepositoryImpl(dao = get())
        }

        factory {
            AccountingObjectDetailInteractor(get(), get())
        }

        factory { (args: AccountingObjectDetailComposeFragmentArgs) ->
            AccountingObjectDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.accountingObjectDetailArguments
            ).create()
        }
    }
}