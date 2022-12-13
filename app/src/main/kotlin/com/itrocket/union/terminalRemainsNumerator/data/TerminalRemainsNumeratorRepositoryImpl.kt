package com.itrocket.union.terminalRemainsNumerator.data

import com.example.union_sync_api.data.TerminalRemainsNumeratorSyncApi
import com.example.union_sync_api.entity.UpdateTerminalRemainsNumerator
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.terminalRemainsNumerator.domain.TerminalRemainsNumeratorDomain
import com.itrocket.union.terminalRemainsNumerator.domain.TerminalRemainsNumeratorRepository
import kotlinx.coroutines.withContext

class TerminalRemainsNumeratorRepositoryImpl(
    private val terminalRemainsNumeratorSyncApi: TerminalRemainsNumeratorSyncApi,
    private val coreDispatchers: CoreDispatchers
) :
    TerminalRemainsNumeratorRepository {
    override suspend fun getTerminalRemainsNumeratorById(remainsId: String): TerminalRemainsNumeratorDomain? {
        return withContext(coreDispatchers.io) {
            terminalRemainsNumeratorSyncApi.getNumeratorByRemainId(remainsId)?.toDomain()
        }
    }

    override suspend fun createTerminalRemainsNumerator(
        numerator: TerminalRemainsNumeratorDomain,
        userInserted: String?
    ) {
        return withContext(coreDispatchers.io) {
            terminalRemainsNumeratorSyncApi.createTerminalRemainsNumerator(
                numerator = numerator.toSyncEntity(),
                userInserted = userInserted
            )
        }
    }

    override suspend fun updateTerminalRemainsNumerator(update: UpdateTerminalRemainsNumerator) {
        return withContext(coreDispatchers.io){
            terminalRemainsNumeratorSyncApi.updateTerminalRemainsNumerator(update)
        }
    }
}