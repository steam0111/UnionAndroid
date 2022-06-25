package com.itrocket.union.equipmentTypeDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.equipmentTypeDetail.data.EquipmentTypeDetailRepositoryImpl
import com.itrocket.union.equipmentTypeDetail.domain.EquipmentTypeDetailInteractor
import com.itrocket.union.equipmentTypeDetail.domain.dependencies.EquipmentTypeDetailRepository
import com.itrocket.union.equipmentTypeDetail.presentation.store.EquipmentTypeDetailStore
import com.itrocket.union.equipmentTypeDetail.presentation.store.EquipmentTypeDetailStoreFactory
import com.itrocket.union.equipmentTypeDetail.presentation.view.EquipmentTypeDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object EquipmentTypeDetailModule {
    val EQUIPMENT_TYPE_DETAIL_VIEW_MODEL_QUALIFIER =
        named("EQUIPMENT_TYPE_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(EQUIPMENT_TYPE_DETAIL_VIEW_MODEL_QUALIFIER) { (args: EquipmentTypeDetailComposeFragmentArgs) ->
            BaseViewModel(get<EquipmentTypeDetailStore>() {
                parametersOf(args)
            })
        }

        factory<EquipmentTypeDetailRepository> {
            EquipmentTypeDetailRepositoryImpl(get())
        }

        factory {
            EquipmentTypeDetailInteractor(get(), get())
        }

        factory { (args: EquipmentTypeDetailComposeFragmentArgs) ->
            EquipmentTypeDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.equipmentTypeDetailComposeFragmentArgs
            ).create()
        }
    }
}