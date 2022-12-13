package com.example.union_sync_api.data

import com.example.union_sync_api.entity.TerminalInfoSyncEntity

interface TerminalInfoSyncApi {
    suspend fun getTerminalInfo(): TerminalInfoSyncEntity?
}