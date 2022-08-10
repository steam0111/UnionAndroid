package com.itrocket.union.moduleSettings

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
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
    private val KEY_CODE_PREFERENCE_KEY = named("KEY_CODE_PREFERENCE_KEY")
    private val READER_POWER_PREFERENCE_KEY = named("READER_POWER_PREFERENCE_KEY")

    val module = module {
        viewModel(MODULESETTINGS_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ModuleSettingsStore>())
        }

        factory<ModuleSettingsRepository> {
            ModuleSettingsRepositoryImpl(
                dataStore = get(),
                keyCodePreferencesKey = get(KEY_CODE_PREFERENCE_KEY),
                readerPowerPreferencesKey = get(READER_POWER_PREFERENCE_KEY)
            )
        }

        factory {
            ModuleSettingsInteractor(get(), get(), get())
        }

        factory {
            ModuleSettingsStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get()
            ).create()
        }

        single(qualifier = KEY_CODE_PREFERENCE_KEY) {
            intPreferencesKey(KEY_CODE_PREFERENCE_KEY.value)
        }

        single(qualifier = READER_POWER_PREFERENCE_KEY) {
            stringPreferencesKey(READER_POWER_PREFERENCE_KEY.value)
        }
    }
}