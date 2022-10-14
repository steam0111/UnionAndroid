package com.itrocket.union.nomenclatureGroupDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.nomenclatureGroupDetail.data.NomenclatureGroupDetailRepositoryImpl
import com.itrocket.union.nomenclatureGroupDetail.domain.NomenclatureGroupDetailInteractor
import com.itrocket.union.nomenclatureGroupDetail.domain.dependencies.NomenclatureGroupDetailRepository
import com.itrocket.union.nomenclatureGroupDetail.presentation.store.NomenclatureGroupDetailStore
import com.itrocket.union.nomenclatureGroupDetail.presentation.store.NomenclatureGroupDetailStoreFactory
import com.itrocket.union.nomenclatureGroupDetail.presentation.view.NomenclatureGroupDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object NomenclatureGroupDetailModule {
    val NOMENCLATURE_GROUP_DETAIL_VIEW_MODEL_QUALIFIER =
        named("NOMENCLATURE_GROUP_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(NOMENCLATURE_GROUP_DETAIL_VIEW_MODEL_QUALIFIER) { (args: NomenclatureGroupDetailComposeFragmentArgs) ->
            BaseViewModel(get<NomenclatureGroupDetailStore>() {
                parametersOf(args)
            })
        }

        factory<NomenclatureGroupDetailRepository> {
            NomenclatureGroupDetailRepositoryImpl(get())
        }

        factory {
            NomenclatureGroupDetailInteractor(get(), get())
        }

        factory { (args: NomenclatureGroupDetailComposeFragmentArgs) ->
            NomenclatureGroupDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.nomenclatureGroupDetailComposeFragmentArgs,
                get()
            ).create()
        }
    }
}