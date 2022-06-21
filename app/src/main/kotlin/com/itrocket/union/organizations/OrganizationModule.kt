package com.itrocket.union.organizations

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.organizations.data.OrganizationRepositoryImpl
import com.itrocket.union.organizations.domain.OrganizationInteractor
import com.itrocket.union.organizations.domain.dependencies.OrganizationRepository
import com.itrocket.union.organizations.presentation.store.OrganizationStore
import com.itrocket.union.organizations.presentation.store.OrganizationStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object OrganizationModule {
    val ORGANIZATION_VIEW_MODEL_QUALIFIER = named("ORGANIZATION_VIEW_MODEL")

    val module = module {
        viewModel(ORGANIZATION_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<OrganizationStore>())
        }

        factory<OrganizationRepository> {
            OrganizationRepositoryImpl(get())
        }

        factory {
            OrganizationInteractor(get())
        }

        factory {
            OrganizationStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get()
            ).create()
        }
    }
}