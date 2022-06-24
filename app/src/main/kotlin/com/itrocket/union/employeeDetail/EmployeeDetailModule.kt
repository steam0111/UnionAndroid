package com.itrocket.union.employeeDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.employeeDetail.data.EmployeeDetailRepositoryImpl
import com.itrocket.union.employeeDetail.domain.EmployeeDetailInteractor
import com.itrocket.union.employeeDetail.domain.dependencies.EmployeeDetailRepository
import com.itrocket.union.employeeDetail.presentation.store.EmployeeDetailStore
import com.itrocket.union.employeeDetail.presentation.store.EmployeeDetailStoreFactory
import com.itrocket.union.employeeDetail.presentation.view.EmployeeDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object EmployeeDetailModule {
    val EMPLOYEE_DETAIL_VIEW_MODEL_QUALIFIER = named("EMPLOYEE_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(EMPLOYEE_DETAIL_VIEW_MODEL_QUALIFIER) { (args: EmployeeDetailComposeFragmentArgs) ->
            BaseViewModel(get<EmployeeDetailStore>() {
                parametersOf(args)
            })
        }

        factory<EmployeeDetailRepository> {
            EmployeeDetailRepositoryImpl(get())
        }

        factory {
            EmployeeDetailInteractor(get(), get())
        }

        factory { (args: EmployeeDetailComposeFragmentArgs) ->
            EmployeeDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.employeeDetailComposeFragmentArgs
            ).create()
        }
    }
}