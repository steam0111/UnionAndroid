package com.itrocket.union.theme.data

import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.theme.domain.dependencies.ColorRepository
import com.itrocket.union.theme.domain.entity.ColorSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ColorRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val colorSettings: ColorSettings,
    private val mainColorPreferenceKey: Preferences.Key<String>,
    private val mainTextColorPreferenceKey: Preferences.Key<String>,
    private val secondaryTextColorPreferenceKey: Preferences.Key<String?>,
    private val appBarBackgroundColorPreferenceKey: Preferences.Key<String?>,
    private val appBarTextColorPreferenceKey: Preferences.Key<String?>,
) : ColorRepository {

    override suspend fun saveColorSettings(
        mainColor: String,
        mainTextColor: String,
        secondaryTextColor: String?,
        appBarBackgroundColor: String?,
        appBarTextColor: String?
    ) {
        dataStore.edit {
            it[mainColorPreferenceKey] = mainColor.toHexColor()
            it[mainTextColorPreferenceKey] = mainTextColor.toHexColor()
            it[secondaryTextColorPreferenceKey] = secondaryTextColor?.toHexColor()
            it[appBarBackgroundColorPreferenceKey] =
                appBarBackgroundColor?.toHexColor() ?: mainColor
            it[appBarTextColorPreferenceKey] = appBarTextColor?.toHexColor()
        }
    }

    override suspend fun initColorSettings() {
        val mainColor = dataStore.data.map { it[mainColorPreferenceKey] }.firstOrNull()
        val mainTextColor = dataStore.data.map { it[mainTextColorPreferenceKey] }.firstOrNull()
        val secondaryTextColor =
            dataStore.data.map { it[secondaryTextColorPreferenceKey] }.firstOrNull()
        val appBarBackgroundColor =
            dataStore.data.map { it[appBarBackgroundColorPreferenceKey] }.firstOrNull()
        val appBarTextColor = dataStore.data.map { it[appBarTextColorPreferenceKey] }.firstOrNull()

        mainColor?.let {
            colorSettings.mainColor = Color(it.toHexColor().toLong(16))
        }
        mainTextColor?.let {
            colorSettings.mainTextColor = Color(it.toHexColor().toLong(16))
        }
        secondaryTextColor?.let {
            colorSettings.secondaryColor = Color(it.toHexColor().toLong(16))
        }
        appBarBackgroundColor?.let {
            colorSettings.appBarBackgroundColor = Color(it.toHexColor().toLong(16))
        }
        appBarTextColor?.let {
            colorSettings.appBarTextColor = Color(it.toHexColor().toLong(16))
        }
    }

    private fun String.toHexColor() = replace("#", "FF")
}