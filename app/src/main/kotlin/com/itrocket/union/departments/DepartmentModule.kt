package com.itrocket.union.departments

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.departments.data.DepartmentRepositoryImpl
import com.itrocket.union.departments.domain.DepartmentInteractor
import com.itrocket.union.departments.domain.dependencies.DepartmentRepository
import com.itrocket.union.departments.presentation.store.DepartmentStore
import com.itrocket.union.departments.presentation.store.DepartmentStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DepartmentModule {
    val DEPARTMENT_VIEW_MODEL_QUALIFIER = named("DEPARTMENT_VIEW_MODEL")

    val module = module {
        viewModel(DEPARTMENT_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<DepartmentStore>())
        }

        factory<DepartmentRepository> {
            DepartmentRepositoryImpl(get(), get())
        }

        factory {
            DepartmentInteractor(get(), get())
        }

        factory {
            DepartmentStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get()
            ).create()
        }
    }
}