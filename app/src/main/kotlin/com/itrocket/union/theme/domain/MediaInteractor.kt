package com.itrocket.union.theme.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.theme.domain.dependencies.MediaRepository
import com.itrocket.union.theme.domain.entity.Medias
import java.io.File
import kotlinx.coroutines.withContext

class MediaInteractor(
    private val mediaRepository: MediaRepository,
    private val coreDispatchers: CoreDispatchers
) {

    private var medias: Medias? = null

    suspend fun saveMedias(headerFile: File?, logoFile: File?) {
        withContext(coreDispatchers.io) {
            mediaRepository.saveMedias(headerFile = headerFile, logoFile = logoFile)
        }
    }

    suspend fun getMedias(): Medias {
        return withContext(coreDispatchers.io) {
            if (medias == null) {
                medias = mediaRepository.getMedias()
            }
            requireNotNull(medias)
        }
    }

    suspend fun removeMedias() = withContext(coreDispatchers.io) {
        mediaRepository.removeMedias()
    }
}