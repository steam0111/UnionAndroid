package com.itrocket.union.equipmentTypes

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.equipmentTypes.data.EquipmentTypeRepositoryImpl
import com.itrocket.union.equipmentTypes.domain.EquipmentTypeInteractor
import com.itrocket.union.equipmentTypes.domain.dependencies.EquipmentTypeRepository
import com.itrocket.union.equipmentTypes.presentation.store.EquipmentTypeStore
import com.itrocket.union.equipmentTypes.presentation.store.EquipmentTypeStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object EquipmentTypeModule {
    val EQUIPMENT_TYPES_MODEL_QUALIFIER = named("EQUIPMENT_TYPES_VIEW_MODEL")

    val module = module {
        viewModel(EQUIPMENT_TYPES_MODEL_QUALIFIER) {
            BaseViewModel(get<EquipmentTypeStore>())
        }

        factory<EquipmentTypeRepository> {
            EquipmentTypeRepositoryImpl(get(), get())
        }

        factory {
            EquipmentTypeInteractor(get(), get())
        }

        factory {
            EquipmentTypeStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }

    }
}