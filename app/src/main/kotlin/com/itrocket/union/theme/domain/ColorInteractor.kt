package com.itrocket.union.theme.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.theme.domain.dependencies.ColorRepository
import kotlinx.coroutines.withContext

class ColorInteractor(
    private val repository: ColorRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun saveColorSettings(
        mainColor: String,
        mainTextColor: String,
        secondaryTextColor: String?,
        appBarBackgroundColor: String?,
        appBarTextColor: String?
    ) {
        withContext(coreDispatchers.io) {
            repository.saveColorSettings(
                mainColor = mainColor,
                mainTextColor = mainTextColor,
                secondaryTextColor = secondaryTextColor,
                appBarTextColor = appBarTextColor,
                appBarBackgroundColor = appBarBackgroundColor
            )
        }
    }

    suspend fun initColorSettings() {
        withContext(coreDispatchers.io) {
            repository.initColorSettings()
        }
    }

}