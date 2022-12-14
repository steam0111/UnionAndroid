package com.itrocket.union.terminalInfo

import org.koin.dsl.module

object TerminalInfoModule {

    val module = module {
        factory<TerminalInfoRepository> { TerminalInfoRepositoryImpl(terminalInfoSyncApi = get()) }
    }
}