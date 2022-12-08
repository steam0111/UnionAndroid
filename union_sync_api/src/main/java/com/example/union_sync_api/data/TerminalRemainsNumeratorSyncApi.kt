package com.example.union_sync_api.data

import com.example.union_sync_api.entity.TerminalRemainsNumeratorSyncEntity

interface TerminalRemainsNumeratorSyncApi {

    suspend fun getAllRemainsNumerators(): List<TerminalRemainsNumeratorSyncEntity>

    suspend fun getNumeratorByRemainId(id: String): TerminalRemainsNumeratorSyncEntity
}