package com.itrocket.union.serverConnect.domain.dependencies

import com.itrocket.union.serverConnect.domain.entity.ColorDomain
import java.io.File

interface StyleRepository {

    suspend fun getStyleSettings(): ColorDomain

    suspend fun getLogoFile(): File

    suspend fun getHeaderFile(): File
}