package com.example.union_sync_impl.sync

import android.util.Log
import com.example.union_sync_api.entity.EnumType
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow
import org.openapitools.client.custom_api.SyncControllerApi
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.AccountingObjectSimpleAdditionalFieldDao
import com.example.union_sync_impl.dao.AccountingObjectUnionImageDao
import com.example.union_sync_impl.dao.AccountingObjectVocabularyAdditionalFieldDao
import com.example.union_sync_impl.dao.ActionRecordDao
import com.example.union_sync_impl.dao.ActionRemainsRecordDao
import com.example.union_sync_impl.dao.CounterpartyDao
import com.example.union_sync_impl.dao.DocumentDao
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.EnumsDao
import com.example.union_sync_impl.dao.EquipmentTypeDao
import com.example.union_sync_impl.dao.InventoryCheckerDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.InventoryRecordDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.dao.OrderDao
import com.example.union_sync_impl.dao.ProducerDao
import com.example.union_sync_impl.dao.ProviderDao
import com.example.union_sync_impl.dao.ReceptionItemCategoryDao
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_impl.dao.AccountingObjectsSimpleCharacteristicsDao
import com.example.union_sync_impl.dao.SimpleAdditionalFieldDao
import com.example.union_sync_impl.dao.StructuralDao
import com.example.union_sync_impl.dao.StructuralPathDao
import com.example.union_sync_impl.dao.TransitAccountingObjectRecordDao
import com.example.union_sync_impl.dao.TransitDao
import com.example.union_sync_impl.dao.TransitRemainsRecordDao
import com.example.union_sync_impl.dao.AccountingObjectsVocabularyCharacteristicsDao
import com.example.union_sync_impl.dao.EmployeeWorkPlaceDao
import com.example.union_sync_impl.dao.InventoryNomenclatureRecordDao
import com.example.union_sync_impl.dao.LabelTypeDao
import com.example.union_sync_impl.dao.SimpleCharacteristicDao
import com.example.union_sync_impl.dao.SyncDao
import com.example.union_sync_impl.dao.TerminalRemainsNumeratorDao
import com.example.union_sync_impl.dao.VocabularyAdditionalFieldDao
import com.example.union_sync_impl.dao.VocabularyAdditionalFieldValueDao
import com.example.union_sync_impl.dao.VocabularyCharacteristicDao
import com.example.union_sync_impl.dao.VocabularyCharacteristicValueDao
import com.example.union_sync_impl.dao.sqlAccountingObjectSimpledQuery
import com.example.union_sync_impl.dao.sqlAccountingObjectUnionImageQuery
import com.example.union_sync_impl.dao.sqlActionRecordQuery
import com.example.union_sync_impl.dao.sqlActionRemainsRecordQuery
import com.example.union_sync_impl.dao.sqlDocumentsSimpledQuery
import com.example.union_sync_impl.dao.sqlInventoryNomenclatureRecordQuery
import com.example.union_sync_impl.dao.sqlInventoryRecordQuery
import com.example.union_sync_impl.dao.sqlInventorySimpledQuery
import com.example.union_sync_impl.dao.sqlRemoveDeletedItemsQuery
import com.example.union_sync_impl.dao.sqlReserveSimpledQuery
import com.example.union_sync_impl.dao.sqlTerminalRemainsNumeratorQuery
import com.example.union_sync_impl.data.mapper.toAccountingObjectDb
import com.example.union_sync_impl.data.mapper.toAccountingObjectDtosV2
import com.example.union_sync_impl.data.mapper.toActionDtoV2
import com.example.union_sync_impl.data.mapper.toActionRecordDb
import com.example.union_sync_impl.data.mapper.toActionRecordDtoV2
import com.example.union_sync_impl.data.mapper.toActionRemainsRecordDb
import com.example.union_sync_impl.data.mapper.toActionRemainsRecordDtoV2
import com.example.union_sync_impl.data.mapper.toCounterpartyDb
import com.example.union_sync_impl.data.mapper.toDb
import com.example.union_sync_impl.data.mapper.toDocumentDb
import com.example.union_sync_impl.data.mapper.toDto
import com.example.union_sync_impl.data.mapper.toEmployeeDb
import com.example.union_sync_impl.data.mapper.toEmployeeWorkPlaceDb
import com.example.union_sync_impl.data.mapper.toEnumDb
import com.example.union_sync_impl.data.mapper.toEquipmentTypeDb
import com.example.union_sync_impl.data.mapper.toInventoryCheckerDb
import com.example.union_sync_impl.data.mapper.toInventoryDb
import com.example.union_sync_impl.data.mapper.toInventoryDtoV2
import com.example.union_sync_impl.data.mapper.toInventoryRecordDb
import com.example.union_sync_impl.data.mapper.toInventoryRecordDtoV2
import com.example.union_sync_impl.data.mapper.toLabelTypeDb
import com.example.union_sync_impl.data.mapper.toLocationDb
import com.example.union_sync_impl.data.mapper.toLocationPathDb
import com.example.union_sync_impl.data.mapper.toLocationTypeDb
import com.example.union_sync_impl.data.mapper.toNomenclatureDb
import com.example.union_sync_impl.data.mapper.toNomenclatureGroupDb
import com.example.union_sync_impl.data.mapper.toOrderDb
import com.example.union_sync_impl.data.mapper.toProducerDb
import com.example.union_sync_impl.data.mapper.toProviderDb
import com.example.union_sync_impl.data.mapper.toReceptionItemCategoryDb
import com.example.union_sync_impl.data.mapper.toRemainsDtoV2
import com.example.union_sync_impl.data.mapper.toReserveDb
import com.example.union_sync_impl.data.mapper.toStructuralDb
import com.example.union_sync_impl.data.mapper.toStructuralPathDb
import com.example.union_sync_impl.data.mapper.toTerminalRemainsNumeratorDtoV2
import com.example.union_sync_impl.data.mapper.toTransitAccountingObjectDb
import com.example.union_sync_impl.data.mapper.toTransitDb
import com.example.union_sync_impl.data.mapper.toTransitRemainsDb
import com.example.union_sync_impl.entity.location.LocationDb
import org.openapitools.client.models.AccountingObjectCharacteristicValueDtoV2
import org.openapitools.client.models.AccountingObjectDtoV2
import org.openapitools.client.models.AccountingObjectSimpleAdditionalFieldValueDtoV2
import org.openapitools.client.models.AccountingObjectUnionImageDtoV2
import org.openapitools.client.models.AccountingObjectVocabularyAdditionalFieldValueDtoV2
import org.openapitools.client.models.AccountingObjectVocabularyCharacteristicValueDtoV2
import org.openapitools.client.models.ActionDtoV2
import org.openapitools.client.models.ActionRecordDtoV2
import org.openapitools.client.models.ActionRemainsRecordDtoV2
import org.openapitools.client.models.CounterpartyDtoV2
import org.openapitools.client.models.EmployeeDtoV2
import org.openapitools.client.models.EmployeeLocationDto
import org.openapitools.client.models.EnumDtoV2
import org.openapitools.client.models.EquipmentTypeDtoV2
import org.openapitools.client.models.InventoryCheckerDto
import org.openapitools.client.models.InventoryDtoV2
import org.openapitools.client.models.InventoryNomenclatureRecordDtoV2
import org.openapitools.client.models.InventoryRecordDtoV2
import org.openapitools.client.models.LabelTypeDtoV2
import org.openapitools.client.models.LocationDtoV2
import org.openapitools.client.models.LocationPathDto
import org.openapitools.client.models.LocationsTypeDtoV2
import org.openapitools.client.models.NomenclatureDtoV2
import org.openapitools.client.models.NomenclatureGroupDtoV2
import org.openapitools.client.models.OrderDtoV2
import org.openapitools.client.models.ProducerDtoV2
import org.openapitools.client.models.RemainsDtoV2
import org.openapitools.client.models.StructuralUnitDtoV2
import org.openapitools.client.models.StructuralUnitPathDtoV2
import org.openapitools.client.models.TerminalRemainsNumeratorDtoV2
import org.openapitools.client.models.TransitAccountingObjectRecordDtoV2
import org.openapitools.client.models.TransitDtoV2
import org.openapitools.client.models.TransitRemainsRecordDtoV2

class SyncRepository(
    private val syncControllerApi: SyncControllerApi,
    private val moshi: Moshi,
    private val accountingObjectsDao: AccountingObjectDao,
    private val employeeDao: EmployeeDao,
    private val nomenclatureDao: NomenclatureDao,
    private val nomenclatureGroupDao: NomenclatureGroupDao,
    private val locationDao: LocationDao,
    private val locationPathDao: LocationPathDao,
    private val providerDao: ProviderDao,
    private val producerDao: ProducerDao,
    private val equipmentTypeDao: EquipmentTypeDao,
    private val counterpartyDao: CounterpartyDao,
    private val orderDao: OrderDao,
    private val receptionItemCategoryDao: ReceptionItemCategoryDao,
    private val reserveDao: ReserveDao,
    private val inventoryDao: InventoryDao,
    private val documentDao: DocumentDao,
    private val actionRecordDao: ActionRecordDao,
    private val actionRemainsRecordDao: ActionRemainsRecordDao,
    private val inventoryRecordDao: InventoryRecordDao,
    private val networkSyncDao: NetworkSyncDao,
    private val structuralDao: StructuralDao,
    private val structuralPathDao: StructuralPathDao,
    private val transitDao: TransitDao,
    private val transitRemainsRecordDao: TransitRemainsRecordDao,
    private val transitAccountingObjectRecordDao: TransitAccountingObjectRecordDao,
    private val enumsDao: EnumsDao,
    private val inventoryCheckerDao: InventoryCheckerDao,
    private val accountingObjectSimpleAdditionalFieldDao: AccountingObjectSimpleAdditionalFieldDao,
    private val accountingObjectVocabularyAdditionalFieldDao: AccountingObjectVocabularyAdditionalFieldDao,
    private val simpleAdditionalFieldDao: SimpleAdditionalFieldDao,
    private val vocabularyAdditionalFieldDao: VocabularyAdditionalFieldDao,
    private val vocabularyAdditionalFieldValueDao: VocabularyAdditionalFieldValueDao,
    private val accountingObjectVocabularyCharacteristicsDao: AccountingObjectsVocabularyCharacteristicsDao,
    private val accountingObjectSimpleCharacteristicsDao: AccountingObjectsSimpleCharacteristicsDao,
    private val simpleCharacteristicDao: SimpleCharacteristicDao,
    private val vocabularyCharacteristicDao: VocabularyCharacteristicDao,
    private val vocabularyCharacteristicValueDao: VocabularyCharacteristicValueDao,
    private val labelTypeDao: LabelTypeDao,
    private val terminalRemainsNumeratorDao: TerminalRemainsNumeratorDao,
    private val accountingObjectUnionImageDao: AccountingObjectUnionImageDao,
    private val workPlaceDao: EmployeeWorkPlaceDao,
    private val syncDao: SyncDao,
    private val inventoryNomenclatureRecordDao: InventoryNomenclatureRecordDao
) {
    suspend fun clearDataBeforeDownload() {
        inventoryDao.clearAll()
        documentDao.clearAll()
    }

    fun getUploadSyncEntities(): List<UploadableSyncEntity> = listOf(
        AccountingObjectSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectDbSaver,
            getAccountingObjectDbCollector(),
            syncDao
        ),
        ReserveSyncEntity(
            syncControllerApi,
            moshi,
            ::reservesDbSaver,
            getRemainsDbCollector(),
            syncDao
        ),
        InventorySyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryDbSaver,
            getInventoryDbCollector(),
            syncDao
        ),
        DocumentSyncEntity(
            syncControllerApi,
            moshi,
            ::documentDbSaver,
            getActionDbCollector(),
            syncDao
        ),
        ActionRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::actionRecordDbSaver,
            getActionRecordDbCollector(),
            syncDao
        ),
        ActionRemainsRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::actionRemainsRecordDbSaver,
            getActionRecordRemainsDbCollector(),
            syncDao
        ),
        TerminalRemainsNumeratorSyncEntity(
            syncControllerApi,
            moshi,
            ::terminalRemainsNumeratorDbSaver,
            getTerminalRemainsNumeratorDbCollector(),
            syncDao
        ),
        InventoryRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryRecordDbSaver,
            getInventoryRecordDbCollector(),
            syncDao
        ),
        InventoryNomenclatureRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryNomenclatureRecordDbSaver,
            getInventoryNomenclatureRecordDbCollector(),
            syncDao
        ),
        AccountingObjectUnionImageSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectUnionImageDbSaver,
            getAccountingObjectUnionImageDbCollector(),
            syncDao
        )
        //Пока не нужен
        /*TransitSyncEntity(
            syncControllerApi,
            moshi,
            ::transitDbSaver,
            getTransitDbCollector()
        ),
        TransitRemainsRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::transitRemainsRecordDbSaver,
            getTransitRecordRemainsDbCollector()
        ),
        TransitAccountingObjectRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::transitRecordDbSaver,
            getTransitRecordAccountingObjectDbCollector()
        ),*/
    )

    fun getSyncEntities(): Map<Pair<String, String>, SyncEntity<*>> = listOf(
        AccountingObjectSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectDbSaver,
            getAccountingObjectDbCollector(),
            syncDao
        ),
        LocationSyncEntity(
            syncControllerApi,
            moshi,
            ::locationsDbSaver,
            syncDao
        ),
        LocationTypeSyncEntity(
            syncControllerApi,
            moshi,
            ::locationTypesDbSaver,
            syncDao
        ),
        CounterpartySyncEntity(
            syncControllerApi,
            moshi,
            ::counterpartiesDbSaver,
            syncDao
        ),
        EmployeeSyncEntity(
            syncControllerApi,
            moshi,
            ::employeesDbSaver,
            syncDao
        ),
        EquipmentSyncEntity(
            syncControllerApi,
            moshi,
            ::equipmentsDbSaver,
            syncDao
        ),
        NomenclatureGroupSyncEntity(
            syncControllerApi,
            moshi,
            ::nomenclatureGroupsDbSaver,
            syncDao
        ),
        NomenclatureSyncEntity(
            syncControllerApi,
            moshi,
            ::nomenclaturesDbSaver,
            syncDao
        ),
        OrderSyncEntity(
            syncControllerApi,
            moshi,
            ::ordersDbSaver,
            syncDao
        ),
        ProducerSyncEntity(
            syncControllerApi,
            moshi,
            ::producersDbSaver,
            syncDao
        ),
        EmployeeWorkPlaceSyncEntity(
            syncControllerApi,
            moshi,
            ::employeeWorkPlacesDbSaver,
            syncDao
        ),
        ReceptionCategoryItemSyncEntity(
            syncControllerApi,
            moshi,
            ::receptionItemCategoryDbSaver,
            syncDao
        ),
        AccountingObjectCategorySyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectCategoryDbSaver,
            syncDao
        ),
        AccountingObjectVocabularyAdditionalFieldSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectVocabularyAdditionalFieldDbSaver,
            syncDao
        ),
        TerminalRemainsNumeratorSyncEntity(
            syncControllerApi,
            moshi,
            ::terminalRemainsNumeratorDbSaver,
            getTerminalRemainsNumeratorDbCollector(),
            syncDao
        ),
        AccountingObjectSimpleAdditionalFieldSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectSimpleAdditionalFieldDbSaver,
            syncDao
        ),
        AccountingObjectSimpleCharacteristicFieldSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectSimpleACharacteristicsDbSaver,
            syncDao
        ),
        AccountingObjectVocabularyCharacteristicFieldSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectVocabularyCharacteristicsDbSaver,
            syncDao
        ),
        InventoryBaseSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryBaseDbSaver,
            syncDao
        ),
        ReserveSyncEntity(
            syncControllerApi,
            moshi,
            ::reservesDbSaver,
            getRemainsDbCollector(),
            syncDao
        ),
        InventorySyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryDbSaver,
            getInventoryDbCollector(),
            syncDao
        ),
        DocumentSyncEntity(
            syncControllerApi,
            moshi,
            ::documentDbSaver,
            getActionDbCollector(),
            syncDao
        ),
        ActionRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::actionRecordDbSaver,
            getActionRecordDbCollector(),
            syncDao
        ),
        ActionRemainsRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::actionRemainsRecordDbSaver,
            getActionRecordRemainsDbCollector(),
            syncDao
        ),
        LocationPathSyncEntity(
            syncControllerApi,
            moshi,
            ::locationPathDbSaver,
            syncDao
        ),
        InventoryRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryRecordDbSaver,
            getInventoryRecordDbCollector(),
            syncDao
        ),
        InventoryNomenclatureRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryNomenclatureRecordDbSaver,
            getInventoryNomenclatureRecordDbCollector(),
            syncDao
        ),
        InventoryCheckerSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryCheckerDbSaver,
            syncDao
        ),
        ActionBasesSyncEntity(
            syncControllerApi,
            moshi,
            ::actionBasesDbSaver,
            syncDao
        ),
        StructuralSyncEntity(
            syncControllerApi,
            moshi,
            ::structuralsDbSaver,
            syncDao
        ),
        StructuralPathSyncEntity(
            syncControllerApi,
            moshi,
            ::structuralPathDbSaver,
            syncDao
        ),
        //Пока не нужен
        /*TransitSyncEntity(
            syncControllerApi,
            moshi,
            ::transitDbSaver,
            getTransitDbCollector()
        ),
        TransitRemainsRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::transitRemainsRecordDbSaver,
            getTransitRecordRemainsDbCollector()
        ),
        TransitAccountingObjectRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::transitRecordDbSaver,
            getTransitRecordAccountingObjectDbCollector()
        ),*/
        AccountingObjectStatusSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectStatusDbSaver,
            syncDao
        ),
        ActionStatusSyncEntity(
            syncControllerApi,
            moshi,
            ::actionStatusDbSaver,
            syncDao
        ),
        ActionTypeSyncEntity(
            syncControllerApi,
            moshi,
            ::actionTypeDbSaver,
            syncDao
        ),
        EmployeeStatusSyncEntity(
            syncControllerApi,
            moshi,
            ::employeeStatusDbSaver,
            syncDao
        ),
        EntityModelTypeSyncEntity(
            syncControllerApi,
            moshi,
            ::entityModelTypeDbSaver,
            syncDao
        ),
        LabelTypeSyncEntity(
            syncControllerApi,
            moshi,
            ::labelTypeDbSaver,
            syncDao
        ),
        InventoryRecordStatusSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryRecordStatusDbSaver,
            syncDao
        ),
        InventoryStateSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryStateDbSaver,
            syncDao
        ),
        InventoryTypeSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryTypeDbSaver,
            syncDao
        ),
        AccountingObjectUnionImageSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectUnionImageDbSaver,
            getAccountingObjectUnionImageDbCollector(),
            syncDao
        )
    ).associateBy {
        it.id to it.table
    }

    private fun getAccountingObjectDbCollector(): Flow<List<AccountingObjectDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    accountingObjectsDao.getAllSimpled(
                        sqlAccountingObjectSimpledQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObject ->
                    localObject.toAccountingObjectDtosV2()
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getRemainsDbCollector(): Flow<List<RemainsDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    reserveDao.getAllSimpled(
                        sqlReserveSimpledQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toRemainsDtoV2() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getInventoryDbCollector(): Flow<List<InventoryDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    inventoryDao.getAllSimpled(
                        sqlInventorySimpledQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toInventoryDtoV2() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getActionDbCollector(): Flow<List<ActionDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    documentDao.getAllSimpled(
                        sqlDocumentsSimpledQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toActionDtoV2() }
                }
            )
        }.filterNot {
            it.isEmpty()
        }
    }

//    private fun getTransitDbCollector(): Flow<List<TransitDtoV2>> {
//        return flow {
//            paginationEmitter(
//                getData = { limit, offset ->
//                    transitDao.getAll(
//                        sqlTransitQuery(
//                            limit = limit,
//                            offset = offset,
//                            updateDate = getLastSyncTime(),
//                            isNonCancel = false,
//                        )
//                    ).firstOrNull().orEmpty()
//                },
//                localToNetworkMapper = { localObjects ->
//                    localObjects.map { it.transitDb.toTransitDtoV2() }
//                }
//            )
//        }.filterNot {
//            it.isEmpty()
//        }
//    }

//    private fun getTransitRecordRemainsDbCollector(): Flow<List<TransitRemainsRecordDtoV2>> {
//        return flow {
//            paginationEmitter(
//                getData = { limit, offset ->
//                    transitRemainsRecordDao.getAll(
//                        sqlTransitRemainsRecordQuery(
//                            limit = limit,
//                            offset = offset,
//                            updateDate = getLastSyncTime(),
//                            isNonCancel = false,
//                        )
//                    )
//                },
//                localToNetworkMapper = { localObjects ->
//                    localObjects.map { it.toTransitRemainsDb() }
//                }
//            )
//        }.filterNot { it.isEmpty() }
//    }

//    private fun getTransitRecordAccountingObjectDbCollector(): Flow<List<TransitAccountingObjectRecordDtoV2>> {
//        return flow {
//            paginationEmitter(
//                getData = { limit, offset ->
//                    transitAccountingObjectRecordDao.getAll(
//                        sqlTransitRecordQuery(
//                            limit = limit,
//                            offset = offset,
//                            updateDate = getLastSyncTime(),
//                            isNonCancel = false,
//                        )
//                    )
//                },
//                localToNetworkMapper = { localObjects ->
//                    localObjects.map { it.toTransitAccountingObjectDb() }
//                }
//            )
//        }.filterNot { it.isEmpty() }
//    }

    private fun getTerminalRemainsNumeratorDbCollector(): Flow<List<TerminalRemainsNumeratorDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    terminalRemainsNumeratorDao.getAllRemainsNumerators(
                        sqlTerminalRemainsNumeratorQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map {
                        it.toTerminalRemainsNumeratorDtoV2()
                    }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getActionRecordRemainsDbCollector(): Flow<List<ActionRemainsRecordDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    actionRemainsRecordDao.getAll(
                        sqlActionRemainsRecordQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toActionRemainsRecordDtoV2() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getInventoryRecordDbCollector(): Flow<List<InventoryRecordDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    inventoryRecordDao.getAll(
                        sqlInventoryRecordQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toInventoryRecordDtoV2() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getInventoryNomenclatureRecordDbCollector(): Flow<List<InventoryNomenclatureRecordDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    inventoryNomenclatureRecordDao.getAll(
                        sqlInventoryNomenclatureRecordQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toDto() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getAccountingObjectUnionImageDbCollector(): Flow<List<AccountingObjectUnionImageDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    accountingObjectUnionImageDao.getAll(
                        sqlAccountingObjectUnionImageQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toDto() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getActionRecordDbCollector(): Flow<List<ActionRecordDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    actionRecordDao.getAll(
                        sqlActionRecordQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime(),
                            isNonCancel = false,
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toActionRecordDtoV2() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private suspend fun <NetworkEntity, LocalEntity> FlowCollector<List<NetworkEntity>>.paginationEmitter(
        getData: suspend (Long, Long) -> List<LocalEntity>,
        localToNetworkMapper: (List<LocalEntity>) -> List<NetworkEntity>
    ) {
        val limit = IMPORT_LIMIT
        var offset = 0L
        var objects: List<LocalEntity>

        do {
            objects = getData(limit, offset)
            offset += objects.size
            emit(localToNetworkMapper(objects))
        } while (objects.isNotEmpty())
    }

    private suspend fun accountingObjectDbSaver(objects: List<AccountingObjectDtoV2>) {
        employeeDao.insertAll(
            (objects.mapNotNull { it.extendedMol?.toEmployeeDb() } +
                    objects.mapNotNull { it.extendedExploiting?.toEmployeeDb() }).distinctBy { it.id }
        )

        nomenclatureGroupDao.insertAll(
            (
                    objects.mapNotNull { it.extendedNomenclatureGroup?.toNomenclatureGroupDb() } +
                            objects.mapNotNull { it.extendedNomenclature?.extendedNomenclatureGroup?.toNomenclatureGroupDb() }
                    ).distinctBy { it.id }
        )

        nomenclatureDao.insertAll(objects.mapNotNull { it.extendedNomenclature?.toNomenclatureDb() })
        locationDao.insertAll(objects.mapNotNull { it.extendedLocation?.toLocationDb() })
        providerDao.insertAll(objects.mapNotNull { it.extendedProvider?.toProviderDb() })
        enumsDao.insertAll(objects.mapNotNull { it.extendedAccountingObjectStatus?.toEnumDb(EnumType.ACCOUNTING_OBJECT_STATUS) })
        structuralDao.insertAll(objects.mapNotNull { it.extendedStructuralUnit?.toStructuralDb() })
        accountingObjectsDao.insertAll(objects.map { it.toAccountingObjectDb() })
        enumsDao.insertAll(objects.mapNotNull {
            it.extendedAccountingObjectCategory?.toEnumDb(
                EnumType.ACCOUNTING_CATEGORY
            )
        })
    }

    private suspend fun locationsDbSaver(objects: List<LocationDtoV2>) {
        locationDao.insertAll(objects.map { it.toLocationDb() })
    }

    private suspend fun structuralsDbSaver(objects: List<StructuralUnitDtoV2>) {
        structuralDao.insertAll(objects.map { it.toStructuralDb() })
    }

    private suspend fun locationTypesDbSaver(objects: List<LocationsTypeDtoV2>) {
        locationDao.insertAllLocationTypes(objects.map { it.toLocationTypeDb() })
    }

    private suspend fun producersDbSaver(objects: List<ProducerDtoV2>) {
        producerDao.insertAll(objects.map { it.toProducerDb() })
    }

    private suspend fun equipmentsDbSaver(objects: List<EquipmentTypeDtoV2>) {
        equipmentTypeDao.insertAll(objects.map { it.toEquipmentTypeDb() })
    }

    private suspend fun employeeWorkPlacesDbSaver(objects: List<EmployeeLocationDto>) {
        locationDao.insertAll(objects.mapNotNull { it.extendedLocation?.toLocationDb() })
        workPlaceDao.insertAll(objects.map { it.toEmployeeWorkPlaceDb() })
    }

    private suspend fun employeesDbSaver(objects: List<EmployeeDtoV2>) {
        structuralDao.insertAll(objects.mapNotNull { it.extendedStructuralUnit?.toStructuralDb() })

        val locations: List<LocationDb> = buildList {
            objects.map { it.employeeWorkPlaces }.map { workPlaces ->
                workPlaces?.forEach { place ->
                    place.extendedLocation?.toLocationDb()?.let { add(it) }
                }
            }
        }

        locationDao.insertAll(locations)
        employeeDao.insertAll(objects.map { it.toEmployeeDb() })
    }

    private suspend fun counterpartiesDbSaver(objects: List<CounterpartyDtoV2>) {
        counterpartyDao.insertAll(objects.map { it.toCounterpartyDb() })
    }

    private suspend fun nomenclatureGroupsDbSaver(objects: List<NomenclatureGroupDtoV2>) {
        nomenclatureGroupDao.insertAll(objects.map { it.toNomenclatureGroupDb() })
    }

    private suspend fun nomenclaturesDbSaver(objects: List<NomenclatureDtoV2>) {
        nomenclatureGroupDao.insertAll(
            objects.mapNotNull {
                it.extendedNomenclatureGroup?.toNomenclatureGroupDb()
            }
        )
        nomenclatureDao.insertAll(
            objects.map {
                it.toNomenclatureDb()
            }
        )
    }

    private suspend fun reservesDbSaver(objects: List<RemainsDtoV2>) {
        employeeDao.insertAll(
            objects.mapNotNull { it.extendedMol?.toEmployeeDb() }.distinctBy { it.id }
        )

        nomenclatureGroupDao.insertAll(
            (
                    objects.mapNotNull { it.extendedNomenclatureGroup?.toNomenclatureGroupDb() } +
                            objects.mapNotNull { it.extendedNomenclature?.extendedNomenclatureGroup?.toNomenclatureGroupDb() }
                    ).distinctBy { it.id }
        )

        nomenclatureDao.insertAll(objects.mapNotNull { it.extendedNomenclature?.toNomenclatureDb() })
        locationDao.insertAll(objects.mapNotNull { it.extendedLocation?.toLocationDb() })
        structuralDao.insertAll(objects.mapNotNull { it.extendedStructuralUnit?.toStructuralDb() })
        receptionItemCategoryDao.insertAll(objects.mapNotNull { it.extendedReceptionItemCategory?.toReceptionItemCategoryDb() })
        orderDao.insertAll(objects.mapNotNull { it.extendedOrder?.toOrderDb() })
        reserveDao.insertAll(objects.map { it.toReserveDb() })
    }

    private suspend fun ordersDbSaver(objects: List<OrderDtoV2>) {
        orderDao.insertAll(objects.map { it.toOrderDb() })
    }

    private suspend fun receptionItemCategoryDbSaver(objects: List<EnumDtoV2>) {
        receptionItemCategoryDao.insertAll(objects.map { it.toReceptionItemCategoryDb() })
    }

    private suspend fun accountingObjectCategoryDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.ACCOUNTING_CATEGORY) })
    }

    private suspend fun accountingObjectSimpleAdditionalFieldDbSaver(objects: List<AccountingObjectSimpleAdditionalFieldValueDtoV2>) {
        simpleAdditionalFieldDao.insertAll(objects.mapNotNull { it.extendedSimpleAdditionalField?.toDb() })
        accountingObjectSimpleAdditionalFieldDao.insertAll(objects.map { it.toDb() })
    }

    private suspend fun accountingObjectVocabularyAdditionalFieldDbSaver(objects: List<AccountingObjectVocabularyAdditionalFieldValueDtoV2>) {
        vocabularyAdditionalFieldDao.insertAll(objects.mapNotNull { it.extendedVocabularyAdditionalField?.toDb() })
        vocabularyAdditionalFieldValueDao.insertAll(objects.mapNotNull { it.extendedVocabularyAdditionalFieldValue?.toDb() })
        accountingObjectVocabularyAdditionalFieldDao.insertAll(objects.map { it.toDb() })
    }

    private suspend fun accountingObjectSimpleACharacteristicsDbSaver(objects: List<AccountingObjectCharacteristicValueDtoV2>) {
        simpleCharacteristicDao.insertAll(objects.mapNotNull { it.extendedCharacteristic?.toDb() })
        accountingObjectSimpleCharacteristicsDao.insertAll(objects.mapNotNull { it.toDb() })
    }

    private suspend fun accountingObjectVocabularyCharacteristicsDbSaver(objects: List<AccountingObjectVocabularyCharacteristicValueDtoV2>) {
        vocabularyCharacteristicDao.insertAll(objects.mapNotNull { it.extendedVocabularyCharacteristic?.toDb() })
        vocabularyCharacteristicValueDao.insertAll(objects.mapNotNull { it.extendedVocabularyCharacteristicValue?.toDb() })
        accountingObjectVocabularyCharacteristicsDao.insertAll(objects.mapNotNull { it.toDb() })
    }

    private suspend fun inventoryBaseDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.INVENTORY_BASE) })
    }

    private suspend fun inventoryDbSaver(objects: List<InventoryDtoV2>) {
        structuralDao.insertAll(objects.mapNotNull { it.extendedStructuralUnit?.toStructuralDb() })
        employeeDao.insertAll(
            objects.mapNotNull { it.extendedMol?.toEmployeeDb() }.distinctBy { it.id }
        )

        locationDao.insertAll(objects.mapNotNull { it.extendedLocation?.toLocationDb() })
        inventoryDao.insertAll(objects.map { it.toInventoryDb() })
        enumsDao.insertAll(objects.mapNotNull { it.extendedInventoryBase?.toEnumDb(EnumType.INVENTORY_BASE) })
    }

    private suspend fun documentDbSaver(objects: List<ActionDtoV2>) {
        structuralDao.insertAll(objects.mapNotNull { it.extendedStructuralUnit?.toStructuralDb() })
        employeeDao.insertAll(
            objects.mapNotNull { it.extendedMol?.toEmployeeDb() }.distinctBy { it.id }
        )
        documentDao.insertAll(objects.map { it.toDocumentDb() })

        enumsDao.insertAll(
            objects.mapNotNull { it.extendedActionBase?.toEnumDb(EnumType.ACTION_BASE) }
        )
    }

    private suspend fun transitDbSaver(objects: List<TransitDtoV2>) {
        structuralDao.insertAll(objects.mapNotNull { it.extendedStructuralUnitFrom?.toStructuralDb() })
        structuralDao.insertAll(objects.mapNotNull { it.extendedStructuralUnitTo?.toStructuralDb() })
        employeeDao.insertAll(
            objects.mapNotNull { it.extendedReceiving?.toEmployeeDb() } +
                    objects.mapNotNull { it.extendedResponsible?.toEmployeeDb() }
                        .distinctBy { it.id }
        )

        locationDao.insertAll(
            objects.mapNotNull { it.extendedLocationFrom?.toLocationDb() } +
                    objects.mapNotNull { it.extendedLocationTo?.toLocationDb() } +
                    objects.mapNotNull { it.extendedVehicleId?.toLocationDb() }
                        .distinctBy { it.id }
        )

        transitDao.insertAll(objects.map { it.toTransitDb() })
    }

    private suspend fun actionRecordDbSaver(objects: List<ActionRecordDtoV2>) {
        actionRecordDao.insertAll(objects.map { it.toActionRecordDb() })
    }

    private suspend fun actionRemainsRecordDbSaver(objects: List<ActionRemainsRecordDtoV2>) {
        actionRemainsRecordDao.insertAll(objects.map { it.toActionRemainsRecordDb() })
    }

    private suspend fun transitRecordDbSaver(objects: List<TransitAccountingObjectRecordDtoV2>) {
        transitAccountingObjectRecordDao.insertAll(objects.map { it.toTransitAccountingObjectDb() })
    }

    private suspend fun transitRemainsRecordDbSaver(objects: List<TransitRemainsRecordDtoV2>) {
        transitRemainsRecordDao.insertAll(objects.map { it.toTransitRemainsDb() })
    }

    private suspend fun inventoryRecordDbSaver(objects: List<InventoryRecordDtoV2>) {
        inventoryRecordDao.insertAll(objects.map { it.toInventoryRecordDb() })
    }

    private suspend fun inventoryNomenclatureRecordDbSaver(objects: List<InventoryNomenclatureRecordDtoV2>) {
        inventoryNomenclatureRecordDao.insertAll(objects.map { it.toDb() })
    }

    private suspend fun accountingObjectUnionImageDbSaver(objects: List<AccountingObjectUnionImageDtoV2>) {
        accountingObjectUnionImageDao.insertAll(objects.map { it.toDb() })
    }

    private suspend fun inventoryCheckerDbSaver(objects: List<InventoryCheckerDto>) {
        employeeDao.insertAll(objects.mapNotNull { it.extendedEmployee?.toEmployeeDb() })
        inventoryDao.insertAll(objects.mapNotNull { it.extendedInventory?.toInventoryDb() })
        inventoryCheckerDao.insertAll(objects.map { it.toInventoryCheckerDb() })
    }

    private suspend fun actionBasesDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.ACTION_BASE) })
    }

    private suspend fun accountingObjectStatusDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.ACCOUNTING_OBJECT_STATUS) })
    }

    private suspend fun actionStatusDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.ACTION_STATUS) })
    }

    private suspend fun actionTypeDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.ACTION_TYPE) })
    }

    private suspend fun employeeStatusDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.EMPLOYEE_STATUS) })
    }

    private suspend fun entityModelTypeDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.ENTITY_MODEL_TYPE) })
    }

    private suspend fun labelTypeDbSaver(objects: List<LabelTypeDtoV2>) {
        labelTypeDao.insertAll(objects.map { it.toLabelTypeDb() })
    }

    private suspend fun inventoryRecordStatusDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.INVENTORY_RECORD_STATUS) })
    }

    private suspend fun inventoryStateDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.INVENTORY_STATE) })
    }

    private suspend fun inventoryTypeDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.INVENTORY_TYPE) })
    }

    private suspend fun terminalRemainsNumeratorDbSaver(objects: List<TerminalRemainsNumeratorDtoV2>) {
        terminalRemainsNumeratorDao.insertAll(objects.map { it.toDb() })
    }

    private suspend fun locationPathDbSaver(objects: List<LocationPathDto>) {
        locationPathDao.insertAll(
            objects.map { it.toLocationPathDb() }
        )
    }

    private suspend fun structuralPathDbSaver(objects: List<StructuralUnitPathDtoV2>) {
        structuralPathDao.insertAll(
            objects.map { it.toStructuralPathDb() }
        )
    }

    private suspend fun getLastSyncTime(): Long {
        return networkSyncDao.getNetworkSync()?.lastSyncTime ?: 0
    }

    companion object {
        const val IMPORT_LIMIT = 50L
    }
}
