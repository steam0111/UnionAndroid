package com.itrocket.union.terminalInfo

interface TerminalInfoRepository {

    suspend fun getTerminalPrefix(): String?
}