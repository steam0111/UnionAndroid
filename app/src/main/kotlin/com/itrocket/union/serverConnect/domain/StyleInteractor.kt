package com.itrocket.union.serverConnect.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.serverConnect.domain.dependencies.StyleRepository
import com.itrocket.union.serverConnect.domain.entity.ColorDomain
import java.io.File
import kotlinx.coroutines.withContext

class StyleInteractor(
    private val repository: StyleRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getStyleSettings(): ColorDomain {
        return withContext(coreDispatchers.io) { repository.getStyleSettings() }
    }

    suspend fun getLogoFile(): File {
        return withContext(coreDispatchers.io) { repository.getLogoFile() }
    }

    suspend fun getHeaderFile(): File {
        return withContext(coreDispatchers.io) { repository.getHeaderFile() }
    }
}