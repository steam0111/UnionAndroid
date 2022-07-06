package com.itrocket.union.departmentDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.departmentDetail.data.DepartmentDetailRepositoryImpl
import com.itrocket.union.departmentDetail.domain.DepartmentDetailInteractor
import com.itrocket.union.departmentDetail.domain.dependencies.DepartmentDetailRepository
import com.itrocket.union.departmentDetail.presentation.store.DepartmentDetailStore
import com.itrocket.union.departmentDetail.presentation.store.DepartmentDetailStoreFactory
import com.itrocket.union.departmentDetail.presentation.view.DepartmentDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DepartmentDetailModule {
    val DEPARTMENT_DETAIL_VIEW_MODEL_QUALIFIER = named("DEPARTMENT_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(DEPARTMENT_DETAIL_VIEW_MODEL_QUALIFIER) { (args: DepartmentDetailComposeFragmentArgs) ->
            BaseViewModel(get<DepartmentDetailStore>() {
                parametersOf(args)
            })
        }

        factory<DepartmentDetailRepository> {
            DepartmentDetailRepositoryImpl(get())
        }

        factory {
            DepartmentDetailInteractor(get(), get())
        }

        factory { (args: DepartmentDetailComposeFragmentArgs) ->
            DepartmentDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.departmentComposeFragmentArgs
            ).create()
        }
    }
}