package com.itrocket.union.nomenclature

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.nomenclature.data.NomenclatureRepositoryImpl
import com.itrocket.union.nomenclature.domain.NomenclatureInteractor
import com.itrocket.union.nomenclature.domain.dependencies.NomenclatureRepository
import com.itrocket.union.nomenclature.presentation.store.NomenclatureStore
import com.itrocket.union.nomenclature.presentation.store.NomenclatureStoreFactory
import com.itrocket.union.nomenclature.presentation.view.NomenclatureComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object NomenclatureModule {
    val NOMENCLATURE_VIEW_MODEL_QUALIFIER = named("NOMENCLATURE_VIEW_MODEL")

    val module = module {
        viewModel(NOMENCLATURE_VIEW_MODEL_QUALIFIER) { (args: NomenclatureComposeFragmentArgs) ->
            BaseViewModel(get<NomenclatureStore>() {
                parametersOf(args)
            })
        }

        factory<NomenclatureRepository> {
            NomenclatureRepositoryImpl(get(), get())
        }

        factory {
            NomenclatureInteractor(get(), get())
        }

        factory { (args: NomenclatureComposeFragmentArgs) ->
            NomenclatureStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.nomenclatureArguments
            ).create()
        }
    }
}