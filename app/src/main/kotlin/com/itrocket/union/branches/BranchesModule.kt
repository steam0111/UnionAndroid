package com.itrocket.union.branches

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.branches.data.BranchesRepositoryImpl
import com.itrocket.union.branches.domain.BranchesInteractor
import com.itrocket.union.branches.domain.dependencies.BranchesRepository
import com.itrocket.union.branches.presentation.store.BranchesStore
import com.itrocket.union.branches.presentation.store.BranchesStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object BranchesModule {
    val BRANCHES_VIEW_MODEL_QUALIFIER = named("BRANCHES_VIEW_MODEL")

    val module = module {
        viewModel(BRANCHES_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<BranchesStore>())
        }

        factory<BranchesRepository> {
            BranchesRepositoryImpl(get(), get())
        }

        factory {
            BranchesInteractor(get(), get())
        }

        factory {
            BranchesStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get()
            ).create()
        }
    }
}