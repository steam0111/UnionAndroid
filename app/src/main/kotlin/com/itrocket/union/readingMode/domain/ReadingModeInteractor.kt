package com.itrocket.union.readingMode.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.readingMode.domain.dependencies.ReadingModeRepository
import ru.interid.scannerclient.domain.reader.ReaderMode
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ReadingModeInteractor(
    private val repository: ReadingModeRepository,
    private val coreDispatchers: CoreDispatchers,
    private val serviceEntryManager: ServiceEntryManager
) {

    fun changeScanMode(readingMode: ReaderMode) {
        serviceEntryManager.changeScanMode(readingMode)
    }
}