package com.example.union_sync_impl.sync

import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.ActionRecordDao
import com.example.union_sync_impl.dao.ActionRemainsRecordDao
import com.example.union_sync_impl.dao.DocumentDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.InventoryRecordDao
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_impl.dao.TerminalRemainsNumeratorDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.dao.sqlActionRecordQuery
import com.example.union_sync_impl.dao.sqlActionRemainsRecordQuery
import com.example.union_sync_impl.dao.sqlDocumentsQuery
import com.example.union_sync_impl.dao.sqlInventoryQuery
import com.example.union_sync_impl.dao.sqlInventoryRecordQuery
import com.example.union_sync_impl.dao.sqlReserveQuery
import com.example.union_sync_impl.dao.sqlTerminalRemainsNumeratorQuery
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.openapitools.client.models.SyncInformationV2

class SyncInfoRepository(
    private val syncDao: NetworkSyncDao,
    private val accountingObjectsDao: AccountingObjectDao,
    private val reserveDao: ReserveDao,
    private val inventoryDao: InventoryDao,
    private val documentDao: DocumentDao,
    private val actionRecordDao: ActionRecordDao,
    private val actionRemainsRecordDao: ActionRemainsRecordDao,
    private val inventoryRecordDao: InventoryRecordDao,
    private val terminalRemainsNumeratorDao: TerminalRemainsNumeratorDao,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getLocalItemCount(): Long {
        return withContext(coreDispatchers.io) {
            coroutineScope {
                val accountingObjectCount = async {
                    accountingObjectsDao.getCount(
                        sqlAccountingObjectQuery(
                            updateDate = getLastSyncTime(),
                            isFilterCount = true,
                            isNonCancel = false,
                        )
                    )
                }
                val reserveCount = async {
                    reserveDao.getFilterCount(
                        sqlReserveQuery(
                            updateDate = getLastSyncTime(),
                            isFilterCount = true,
                            isNonCancel = false
                        )
                    )
                }
                val inventoryCount = async {
                    inventoryDao.getCount(
                        sqlInventoryQuery(
                            updateDate = getLastSyncTime(),
                            isFilterCount = true,
                            isNonCancel = false,
                        )
                    )
                }
                val documentCount = async {
                    documentDao.getCount(
                        sqlDocumentsQuery(
                            updateDate = getLastSyncTime(),
                            isFilterCount = true,
                            isNonCancel = false,
                        )
                    )
                }
                val actionRecordCount = async {
                    actionRecordDao.getCount(
                        sqlActionRecordQuery(
                            updateDate = getLastSyncTime(),
                            isFilterCount = true,
                            isNonCancel = false,
                        )
                    )
                }
                val actionRemainsRecordCount = async {
                    actionRemainsRecordDao.getCount(
                        sqlActionRemainsRecordQuery(
                            updateDate = getLastSyncTime(),
                            isFilterCount = true,
                            isNonCancel = false,
                        )
                    )
                }
                val inventoryRecordCount = async {
                    inventoryRecordDao.getCount(
                        sqlInventoryRecordQuery(
                            updateDate = getLastSyncTime(),
                            isFilterCount = true,
                            isNonCancel = false,
                        )
                    )
                }
                val terminalRemainsNumeratorCount = async {
                    terminalRemainsNumeratorDao.getCount(
                        sqlTerminalRemainsNumeratorQuery(
                            updateDate = getLastSyncTime(),
                            isFilterCount = true,
                            isNonCancel = false
                        )
                    )
                }
                val allCount =
                    accountingObjectCount.await() + reserveCount.await() + inventoryCount.await() +
                            documentCount.await() + actionRecordCount.await() + actionRemainsRecordCount.await() +
                            inventoryRecordCount.await() + terminalRemainsNumeratorCount.await()
                allCount
            }
        }
    }

    suspend fun getServerItemCount(
        syncEntities: Map<Pair<String, String>, SyncEntity<*>>,
        exportSyncInfo: SyncInformationV2
    ): Long {
        return withContext(coreDispatchers.io) {
            val entityIds = syncEntities.map { it.key.first }
            val exportPartsInformation =
                exportSyncInfo.exportPartBufferInformation.exportPartsInformation

            exportPartsInformation
                .filter { entityIds.contains(it.entityModel.id) }
                .sumOf {
                    it.count ?: 0
                }.toLong()
        }
    }

    suspend fun getLastSyncTime(): Long {
        return withContext(coreDispatchers.io) {
            syncDao.getNetworkSync()?.lastSyncTime ?: 0
        }
    }
}