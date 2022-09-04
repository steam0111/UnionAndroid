package com.itrocket.union.theme.domain.dependencies

import com.itrocket.union.theme.domain.entity.Medias
import java.io.File

interface MediaRepository {

    suspend fun saveMedias(logoFile: File?, headerFile: File?)

    suspend fun getMedias(): Medias
}