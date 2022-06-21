package com.itrocket.union.nomenclatureGroup

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.nomenclatureGroup.data.NomenclatureGroupRepositoryImpl
import com.itrocket.union.nomenclatureGroup.domain.NomenclatureGroupInteractor
import com.itrocket.union.nomenclatureGroup.domain.dependencies.NomenclatureGroupRepository
import com.itrocket.union.nomenclatureGroup.presentation.store.NomenclatureGroupStore
import com.itrocket.union.nomenclatureGroup.presentation.store.NomenclatureGroupStoreFactory
import com.itrocket.union.nomenclatureGroup.presentation.view.NomenclatureGroupComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object NomenclatureGroupModule {
    val NOMENCLATUREGROUP_VIEW_MODEL_QUALIFIER = named("NOMENCLATUREGROUP_VIEW_MODEL")

    val module = module {
        viewModel(NOMENCLATUREGROUP_VIEW_MODEL_QUALIFIER) { (args: NomenclatureGroupComposeFragmentArgs) ->
            BaseViewModel(get<NomenclatureGroupStore>() {
                parametersOf(args)
            })
        }

        factory<NomenclatureGroupRepository> {
            NomenclatureGroupRepositoryImpl(get(), get())
        }

        factory {
            NomenclatureGroupInteractor(get(), get())
        }

        factory { (args: NomenclatureGroupComposeFragmentArgs) ->
            NomenclatureGroupStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.nomenclatureGroupArguments,
                get()
            ).create()
        }
    }
}