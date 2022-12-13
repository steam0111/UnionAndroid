package com.example.union_sync_api.data

import com.example.union_sync_api.entity.TerminalRemainsNumeratorSyncEntity
import com.example.union_sync_api.entity.UpdateTerminalRemainsNumerator

interface TerminalRemainsNumeratorSyncApi {

    suspend fun getAllRemainsNumerators(): List<TerminalRemainsNumeratorSyncEntity>

    suspend fun getNumeratorByRemainId(id: String): TerminalRemainsNumeratorSyncEntity?

    suspend fun createTerminalRemainsNumerator(
        numerator: TerminalRemainsNumeratorSyncEntity,
        userInserted: String?
    )

    suspend fun updateTerminalRemainsNumerator(
        update: UpdateTerminalRemainsNumerator
    )
}