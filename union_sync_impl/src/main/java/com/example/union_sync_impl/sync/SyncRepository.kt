package com.example.union_sync_impl.sync

import com.example.union_sync_api.entity.EnumType
import com.example.union_sync_impl.dao.*
import com.example.union_sync_impl.data.mapper.*
import com.example.union_sync_impl.data.mapper.toActionRecordDb
import com.example.union_sync_impl.data.mapper.toActionRemainsRecordDb
import com.example.union_sync_impl.data.mapper.toReserveDb
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.*
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.*

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
    private val syncDao: NetworkSyncDao,
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
    private val vocabularyAdditionalFieldValueDao: VocabularyAdditionalFieldValueDao
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
            getAccountingObjectDbCollector()
        ),
        ReserveSyncEntity(
            syncControllerApi,
            moshi,
            ::reservesDbSaver,
            getRemainsDbCollector()
        ),
        InventorySyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryDbSaver,
            getInventoryDbCollector()
        ),
        DocumentSyncEntity(
            syncControllerApi,
            moshi,
            ::documentDbSaver,
            getActionDbCollector()
        ),
        ActionRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::actionRecordDbSaver,
            getActionRecordDbCollector()
        ),
        ActionRemainsRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::actionRemainsRecordDbSaver,
            getActionRecordRemainsDbCollector()
        ),
        InventoryRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryRecordDbSaver,
            getInventoryRecordDbCollector()
        ),
        TransitSyncEntity(
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
        ),
    )

    fun getSyncEntities(): Map<Pair<String, String>, SyncEntity<*>> = listOf(
        AccountingObjectSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectDbSaver,
            getAccountingObjectDbCollector()
        ),
        LocationSyncEntity(
            syncControllerApi,
            moshi,
            ::locationsDbSaver
        ),
        LocationTypeSyncEntity(
            syncControllerApi,
            moshi,
            ::locationTypesDbSaver
        ),
        CounterpartySyncEntity(
            syncControllerApi,
            moshi,
            ::counterpartiesDbSaver
        ),
        EmployeeSyncEntity(
            syncControllerApi,
            moshi,
            ::employeesDbSaver
        ),
        EquipmentSyncEntity(
            syncControllerApi,
            moshi,
            ::equipmentsDbSaver
        ),
        NomenclatureGroupSyncEntity(
            syncControllerApi,
            moshi,
            ::nomenclatureGroupsDbSaver
        ),
        NomenclatureSyncEntity(
            syncControllerApi,
            moshi,
            ::nomenclaturesDbSaver
        ),
        OrderSyncEntity(
            syncControllerApi,
            moshi,
            ::ordersDbSaver
        ),
        ProducerSyncEntity(
            syncControllerApi,
            moshi,
            ::producersDbSaver
        ),
        ReceptionCategoryItemSyncEntity(
            syncControllerApi,
            moshi,
            ::receptionItemCategoryDbSaver
        ),
        AccountingObjectCategorySyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectCategoryDbSaver
        ),
        AccountingObjectVocabularyAdditionalFieldSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectVocabularyAdditionalFieldDbSaver
        ),
        AccountingObjectSimpleAdditionalFieldSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectSimpleAdditionalFieldDbSaver
        ),
        InventoryBaseSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryBaseDbSaver
        ),
        ReserveSyncEntity(
            syncControllerApi,
            moshi,
            ::reservesDbSaver,
            getRemainsDbCollector()
        ),
        InventorySyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryDbSaver,
            getInventoryDbCollector()
        ),
        DocumentSyncEntity(
            syncControllerApi,
            moshi,
            ::documentDbSaver,
            getActionDbCollector()
        ),
        ActionRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::actionRecordDbSaver,
            getActionRecordDbCollector()
        ),
        ActionRemainsRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::actionRemainsRecordDbSaver,
            getActionRecordRemainsDbCollector()
        ),
        LocationPathSyncEntity(
            syncControllerApi,
            moshi,
            ::locationPathDbSaver
        ),
        InventoryRecordSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryRecordDbSaver,
            getInventoryRecordDbCollector()
        ),
        InventoryCheckerSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryCheckerDbSaver,
        ),
        ActionBasesSyncEntity(
            syncControllerApi,
            moshi,
            ::actionBasesDbSaver
        ),
        StructuralSyncEntity(
            syncControllerApi,
            moshi,
            ::structuralsDbSaver
        ),
        StructuralPathSyncEntity(
            syncControllerApi,
            moshi,
            ::structuralPathDbSaver
        ),
        TransitSyncEntity(
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
        ),
        AccountingObjectStatusSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectStatusDbSaver
        ),
        ActionStatusSyncEntity(
            syncControllerApi,
            moshi,
            ::actionStatusDbSaver
        ),
        ActionTypeSyncEntity(
            syncControllerApi,
            moshi,
            ::actionTypeDbSaver
        ),
        EmployeeStatusSyncEntity(
            syncControllerApi,
            moshi,
            ::employeeStatusDbSaver
        ),
        EntityModelTypeSyncEntity(
            syncControllerApi,
            moshi,
            ::entityModelTypeDbSaver
        ),
        InventoryRecordStatusSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryRecordStatusDbSaver
        ),
        InventoryStateSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryStateDbSaver
        ),
        InventoryTypeSyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryTypeDbSaver
        ),
    ).associateBy {
        it.id to it.table
    }

    private fun getAccountingObjectDbCollector(): Flow<List<AccountingObjectDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    accountingObjectsDao.getAll(
                        sqlAccountingObjectQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime()
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.toAccountingObjectDtosV2()
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getRemainsDbCollector(): Flow<List<RemainsDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    reserveDao.getAll(
                        sqlReserveQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime()
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.reserveDb.toRemainsDtoV2() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getInventoryDbCollector(): Flow<List<InventoryDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    inventoryDao.getAll(
                        sqlInventoryQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime()
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.inventoryDb.toInventoryDtoV2() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getActionDbCollector(): Flow<List<ActionDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    documentDao.getAll(
                        sqlDocumentsQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime()
                        )
                    ).firstOrNull().orEmpty()
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.documentDb.toActionDtoV2() }
                }
            )
        }.filterNot {
            it.isEmpty()
        }
    }

    private fun getTransitDbCollector(): Flow<List<TransitDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    transitDao.getAll(
                        sqlTransitQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime()
                        )
                    ).firstOrNull().orEmpty()
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.transitDb.toTransitDtoV2() }
                }
            )
        }.filterNot {
            it.isEmpty()
        }
    }

    private fun getTransitRecordRemainsDbCollector(): Flow<List<TransitRemainsRecordDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    transitRemainsRecordDao.getAll(
                        sqlTransitRemainsRecordQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime()
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toTransitRemainsDb() }
                }
            )
        }.filterNot { it.isEmpty() }
    }

    private fun getTransitRecordAccountingObjectDbCollector(): Flow<List<TransitAccountingObjectRecordDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    transitAccountingObjectRecordDao.getAll(
                        sqlTransitRecordQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = getLastSyncTime()
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toTransitAccountingObjectDb() }
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
                            updateDate = getLastSyncTime()
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
                            updateDate = getLastSyncTime()
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.map { it.toInventoryRecordDtoV2() }
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
                            updateDate = getLastSyncTime()
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
        val limit = 50L
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

    private suspend fun employeesDbSaver(objects: List<EmployeeDtoV2>) {
        structuralDao.insertAll(objects.mapNotNull { it.extendedStructuralUnit?.toStructuralDb() })
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

    private suspend fun inventoryRecordStatusDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.INVENTORY_RECORD_STATUS) })
    }

    private suspend fun inventoryStateDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.INVENTORY_STATE) })
    }

    private suspend fun inventoryTypeDbSaver(objects: List<EnumDtoV2>) {
        enumsDao.insertAll(objects.map { it.toEnumDb(EnumType.INVENTORY_TYPE) })
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
        return syncDao.getNetworkSync()?.lastSyncTime ?: 0
    }
}