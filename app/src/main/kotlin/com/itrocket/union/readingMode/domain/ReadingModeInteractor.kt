package com.itrocket.union.readingMode.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.readingMode.domain.dependencies.ReadingModeRepository

class ReadingModeInteractor(
    private val repository: ReadingModeRepository,
    private val coreDispatchers: CoreDispatchers
) {

}