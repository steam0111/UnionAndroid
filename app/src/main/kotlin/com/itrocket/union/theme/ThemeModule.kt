package com.itrocket.union.theme

import androidx.datastore.preferences.core.stringPreferencesKey
import com.itrocket.union.core.CoreModule.UNION_DATA_STORE_QUALIFIER
import com.itrocket.union.theme.data.ColorRepositoryImpl
import com.itrocket.union.theme.data.MediaRepositoryImpl
import com.itrocket.union.theme.domain.ColorInteractor
import com.itrocket.union.theme.domain.MediaInteractor
import com.itrocket.union.theme.domain.dependencies.ColorRepository
import com.itrocket.union.theme.domain.dependencies.MediaRepository
import com.itrocket.union.theme.domain.entity.ColorSettings
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ThemeModule {

    private val MAIN_COLOR_PREFERENCE_KEY = named("MAIN_COLOR_PREFERENCE_KEY")
    private val MAIN_TEXT_COLOR_PREFERENCE_KEY = named("MAIN_TEXT_COLOR_PREFERENCE_KEY")
    private val SECONDARY_TEXT_COLOR_PREFERENCE_KEY = named("SECONDARY_TEXT_COLOR_PREFERENCE_KEY")
    private val APP_BAR_BACKGROUND_COLOR_PREFERENCE_KEY =
        named("APP_BAR_BACKGROUND_COLOR_PREFERENCE_KEY")
    private val APP_BAR_TEXT_COLOR_PREFERENCE_KEY = named("APP_BAR_TEXT_COLOR_PREFERENCE_KEY")

    val module = module {
        single {
            ColorInteractor(repository = get(), coreDispatchers = get())
        }

        single<ColorRepository> {
            ColorRepositoryImpl(
                dataStore = get(UNION_DATA_STORE_QUALIFIER),
                colorSettings = get(),
                mainColorPreferenceKey = get(MAIN_COLOR_PREFERENCE_KEY),
                mainTextColorPreferenceKey = get(MAIN_TEXT_COLOR_PREFERENCE_KEY),
                secondaryTextColorPreferenceKey = get(SECONDARY_TEXT_COLOR_PREFERENCE_KEY),
                appBarBackgroundColorPreferenceKey = get(APP_BAR_BACKGROUND_COLOR_PREFERENCE_KEY),
                appBarTextColorPreferenceKey = get(APP_BAR_TEXT_COLOR_PREFERENCE_KEY)
            )
        }
        single {
            ColorSettings()
        }
        single(qualifier = MAIN_COLOR_PREFERENCE_KEY) {
            stringPreferencesKey(MAIN_COLOR_PREFERENCE_KEY.value)
        }
        single(qualifier = MAIN_TEXT_COLOR_PREFERENCE_KEY) {
            stringPreferencesKey(MAIN_TEXT_COLOR_PREFERENCE_KEY.value)
        }
        single(qualifier = SECONDARY_TEXT_COLOR_PREFERENCE_KEY) {
            stringPreferencesKey(SECONDARY_TEXT_COLOR_PREFERENCE_KEY.value)
        }
        single(qualifier = APP_BAR_BACKGROUND_COLOR_PREFERENCE_KEY) {
            stringPreferencesKey(APP_BAR_BACKGROUND_COLOR_PREFERENCE_KEY.value)
        }
        single(qualifier = APP_BAR_TEXT_COLOR_PREFERENCE_KEY) {
            stringPreferencesKey(APP_BAR_TEXT_COLOR_PREFERENCE_KEY.value)
        }

        factory {
            MediaInteractor(mediaRepository = get(), coreDispatchers = get())
        }

        factory<MediaRepository> {
            MediaRepositoryImpl(applicationContext = androidApplication())
        }
    }
}