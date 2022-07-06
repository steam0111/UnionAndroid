package com.itrocket.union.branchDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.branchDetail.data.BranchDetailRepositoryImpl
import com.itrocket.union.branchDetail.domain.BranchDetailInteractor
import com.itrocket.union.branchDetail.domain.dependencies.BranchDetailRepository
import com.itrocket.union.branchDetail.presentation.store.BranchDetailStore
import com.itrocket.union.branchDetail.presentation.store.BranchDetailStoreFactory
import com.itrocket.union.branchDetail.presentation.view.BranchDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object BranchDetailModule {
    val BRANCH_DETAIL_VIEW_MODEL_QUALIFIER = named("BRANCH_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(BRANCH_DETAIL_VIEW_MODEL_QUALIFIER) { (args: BranchDetailComposeFragmentArgs) ->
            BaseViewModel(get<BranchDetailStore>() {
                parametersOf(args)
            })
        }

        factory<BranchDetailRepository> {
            BranchDetailRepositoryImpl(get())
        }

        factory {
            BranchDetailInteractor(get(), get())
        }

        factory { (args: BranchDetailComposeFragmentArgs) ->
            BranchDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.branchDetailComposeFragmentArgs
            ).create()
        }
    }
}