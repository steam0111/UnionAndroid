package com.itrocket.union.employees

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.employees.data.EmployeeRepositoryImpl
import com.itrocket.union.employees.domain.EmployeeInteractor
import com.itrocket.union.employees.domain.dependencies.EmployeeRepository
import com.itrocket.union.employees.presentation.store.EmployeeStore
import com.itrocket.union.employees.presentation.store.EmployeeStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object EmployeeModule {
    val EMPLOYEE_VIEW_MODEL_QUALIFIER = named("EMPLOYEE_VIEW_MODEL")

    val module = module {
        viewModel(EMPLOYEE_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<EmployeeStore>())
        }

        factory<EmployeeRepository> {
            EmployeeRepositoryImpl(get(), get())
        }

        factory {
            EmployeeInteractor(get(), get())
        }

        factory {
            EmployeeStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}