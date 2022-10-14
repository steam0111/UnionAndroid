package com.itrocket.union.nomenclatureDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.nomenclatureDetail.data.NomenclatureDetailRepositoryImpl
import com.itrocket.union.nomenclatureDetail.domain.NomenclatureDetailInteractor
import com.itrocket.union.nomenclatureDetail.domain.dependencies.NomenclatureDetailRepository
import com.itrocket.union.nomenclatureDetail.presentation.store.NomenclatureDetailStore
import com.itrocket.union.nomenclatureDetail.presentation.store.NomenclatureDetailStoreFactory
import com.itrocket.union.nomenclatureDetail.presentation.view.NomenclatureDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object NomenclatureDetailModule {
    val NOMENCLATURE_DETAIL_VIEW_MODEL_QUALIFIER = named("NOMENCLATURE_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(NOMENCLATURE_DETAIL_VIEW_MODEL_QUALIFIER) { (args: NomenclatureDetailComposeFragmentArgs) ->
            BaseViewModel(get<NomenclatureDetailStore>() {
                parametersOf(args)
            })
        }

        factory<NomenclatureDetailRepository> {
            NomenclatureDetailRepositoryImpl(get())
        }

        factory {
            NomenclatureDetailInteractor(get(), get())
        }

        factory { (args: NomenclatureDetailComposeFragmentArgs) ->
            NomenclatureDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.nomenclatureDetailComposeFragmentArgs,
                get()
            ).create()
        }
    }
}