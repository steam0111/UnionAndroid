package com.itrocket.union.terminalRemainsNumerator

import com.itrocket.union.terminalRemainsNumerator.data.TerminalRemainsNumeratorRepositoryImpl
import com.itrocket.union.terminalRemainsNumerator.domain.TerminalRemainsNumeratorRepository
import org.koin.dsl.module

object TerminalRemainsNumeratorModule {

    val module = module {
        factory<TerminalRemainsNumeratorRepository> {
            TerminalRemainsNumeratorRepositoryImpl(
                terminalRemainsNumeratorSyncApi = get(),
                coreDispatchers = get()
            )
        }
    }
}