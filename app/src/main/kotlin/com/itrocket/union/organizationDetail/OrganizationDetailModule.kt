package com.itrocket.union.organizationDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.organizationDetail.data.OrganizationDetailRepositoryImpl
import com.itrocket.union.organizationDetail.domain.OrganizationDetailInteractor
import com.itrocket.union.organizationDetail.domain.dependencies.OrganizationDetailRepository
import com.itrocket.union.organizationDetail.presentation.store.OrganizationDetailStore
import com.itrocket.union.organizationDetail.presentation.store.OrganizationDetailStoreFactory
import com.itrocket.union.organizationDetail.presentation.view.OrganizationDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object OrganizationDetailModule {
    val ORGANIZATION_DETAIL_VIEW_MODEL_QUALIFIER = named("ORGANIZATION_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(ORGANIZATION_DETAIL_VIEW_MODEL_QUALIFIER) { (args: OrganizationDetailComposeFragmentArgs) ->
            BaseViewModel(get<OrganizationDetailStore>() {
                parametersOf(args)
            })
        }

        factory<OrganizationDetailRepository> {
            OrganizationDetailRepositoryImpl(get())
        }

        factory {
            OrganizationDetailInteractor(get(), get())
        }

        factory { (args: OrganizationDetailComposeFragmentArgs) ->
            OrganizationDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.organizationDetailComposeFragmentArgs
            ).create()
        }
    }
}