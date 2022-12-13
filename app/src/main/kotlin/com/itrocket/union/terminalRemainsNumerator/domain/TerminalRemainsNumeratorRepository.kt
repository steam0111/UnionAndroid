package com.itrocket.union.terminalRemainsNumerator.domain

import com.example.union_sync_api.entity.UpdateTerminalRemainsNumerator

interface TerminalRemainsNumeratorRepository {

    suspend fun getTerminalRemainsNumeratorById(remainsId: String): TerminalRemainsNumeratorDomain?

    suspend fun createTerminalRemainsNumerator(
        numerator: TerminalRemainsNumeratorDomain,
        userInserted: String?
    )

    suspend fun updateTerminalRemainsNumerator(update: UpdateTerminalRemainsNumerator)
}