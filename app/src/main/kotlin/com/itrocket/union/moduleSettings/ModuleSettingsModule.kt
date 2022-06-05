package com.itrocket.union.moduleSettings

import androidx.datastore.preferences.core.intPreferencesKey
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.moduleSettings.data.ModuleSettingsRepositoryImpl
import com.itrocket.union.moduleSettings.domain.ModuleSettingsInteractor
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import com.itrocket.union.moduleSettings.presentation.store.ModuleSettingsStore
import com.itrocket.union.moduleSettings.presentation.store.ModuleSettingsStoreFactory
import com.itrocket.core.base.BaseViewModel

object ModuleSettingsModule {
    val MODULESETTINGS_VIEW_MODEL_QUALIFIER = named("MODULESETTINGS_VIEW_MODEL")
    val KEY_CODE_PREFERENCE_KEY = named("KEY_CODE_PREFERENCE_KEY")

    val module = module {
        viewModel(MODULESETTINGS_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ModuleSettingsStore>())
        }

        factory<ModuleSettingsRepository> {
            ModuleSettingsRepositoryImpl(dataStore = get(), keyCodePreferencesKey = get(KEY_CODE_PREFERENCE_KEY))
        }

        factory {
            ModuleSettingsInteractor(get(), get(), get())
        }

        factory {
            ModuleSettingsStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
            ).create()
        }

        single(qualifier = KEY_CODE_PREFERENCE_KEY) {
            intPreferencesKey(KEY_CODE_PREFERENCE_KEY.value)
        }
    }
}