package com.itrocket.union.moduleSettings

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.core.CoreModule.UNION_DATA_STORE_QUALIFIER
import com.itrocket.union.moduleSettings.data.ModuleSettingsRepositoryImpl
import com.itrocket.union.moduleSettings.domain.ModuleSettingsInteractor
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import com.itrocket.union.moduleSettings.presentation.store.ModuleSettingsStore
import com.itrocket.union.moduleSettings.presentation.store.ModuleSettingsStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ModuleSettingsModule {
    val MODULESETTINGS_VIEW_MODEL_QUALIFIER = named("MODULESETTINGS_VIEW_MODEL")
    private val KEY_CODE_PREFERENCE_KEY = named("KEY_CODE_PREFERENCE_KEY")
    private val READER_POWER_PREFERENCE_KEY = named("READER_POWER_PREFERENCE_KEY")
    private val DYNAMIC_SAVE_INVENTORY_PREFERENCE_KEY =
        named("DYNAMIC_SAVE_INVENTORY_PREFERENCE_KEY")
    private val DEFAULT_READING_MODE_PREFERENCE_KEY = named("DEFAULT_READING_MODE_PREFERENCE_KEY")

    val module = module {
        viewModel(MODULESETTINGS_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ModuleSettingsStore>())
        }

        factory<ModuleSettingsRepository> {
            ModuleSettingsRepositoryImpl(
                dataStore = get(UNION_DATA_STORE_QUALIFIER),
                keyCodePreferencesKey = get(KEY_CODE_PREFERENCE_KEY),
                readerPowerPreferencesKey = get(READER_POWER_PREFERENCE_KEY),
                dynamicSaveInventoryPreferencesKey = get(DYNAMIC_SAVE_INVENTORY_PREFERENCE_KEY),
                readingModePreferencesKey = get(DEFAULT_READING_MODE_PREFERENCE_KEY)
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
                get(),
                get(),
                get()
            ).create()
        }

        single(qualifier = KEY_CODE_PREFERENCE_KEY) {
            intPreferencesKey(KEY_CODE_PREFERENCE_KEY.value)
        }
        single(qualifier = DEFAULT_READING_MODE_PREFERENCE_KEY) {
            stringPreferencesKey(DEFAULT_READING_MODE_PREFERENCE_KEY.value)
        }
        single(qualifier = READER_POWER_PREFERENCE_KEY) {
            intPreferencesKey(READER_POWER_PREFERENCE_KEY.value)
        }
        single(qualifier = DYNAMIC_SAVE_INVENTORY_PREFERENCE_KEY) {
            booleanPreferencesKey(DYNAMIC_SAVE_INVENTORY_PREFERENCE_KEY.value)
        }
    }
}