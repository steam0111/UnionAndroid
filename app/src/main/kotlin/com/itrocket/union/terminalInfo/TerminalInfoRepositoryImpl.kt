package com.itrocket.union.terminalInfo

import com.example.union_sync_api.data.TerminalInfoSyncApi

class TerminalInfoRepositoryImpl(private val terminalInfoSyncApi: TerminalInfoSyncApi) :
    TerminalInfoRepository {
    override suspend fun getTerminalPrefix(): String? {
        return terminalInfoSyncApi.getTerminalInfo()?.terminalPrefix
    }
}