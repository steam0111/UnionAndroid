package com.example.union_sync_impl.sync

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
    private val organizationsDao: OrganizationDao,
    private val employeeDao: EmployeeDao,
    private val nomenclatureDao: NomenclatureDao,
    private val nomenclatureGroupDao: NomenclatureGroupDao,
    private val locationDao: LocationDao,
    private val locationPathDao: LocationPathDao,
    private val departmentDao: DepartmentDao,
    private val providerDao: ProviderDao,
    private val statusesDao: AccountingObjectStatusDao,
    private val regionDao: RegionDao,
    private val producerDao: ProducerDao,
    private val equipmentTypeDao: EquipmentTypeDao,
    private val counterpartyDao: CounterpartyDao,
    private val branchesDao: BranchesDao,
    private val orderDao: OrderDao,
    private val receptionItemCategoryDao: ReceptionItemCategoryDao,
    private val reserveDao: ReserveDao,
    private val inventoryDao: InventoryDao,
    private val documentDao: DocumentDao,
    private val actionRecordDao: ActionRecordDao,
    private val actionRemainsRecordDao: ActionRemainsRecordDao,
    private val inventoryRecordDao: InventoryRecordDao,
    private val actionBaseDao: ActionBaseDao,
    private val syncDao: NetworkSyncDao,
    private val structuralDao: StructuralDao,
    private val structuralPathDao: StructuralPathDao
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
        )
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
        BranchesSyncEntity(
            syncControllerApi,
            moshi,
            ::branchesDbSaver
        ),
        CounterpartySyncEntity(
            syncControllerApi,
            moshi,
            ::counterpartiesDbSaver
        ),
        DepartmentSyncEntity(
            syncControllerApi,
            moshi,
            ::departmentsDbSaver
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
        OrganizationSyncEntity(
            syncControllerApi,
            moshi,
            ::organizationsDbSaver
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
        RegionSyncEntity(
            syncControllerApi,
            moshi,
            ::regionsDbSaver
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
        )
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
                    ).firstOrNull().orEmpty()
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
        organizationsDao.insertAll(
            (
                    objects.mapNotNull { it.extendedOrganization?.toOrganizationDb() } +
                            objects.mapNotNull { it.extendedDepartment?.extendedOrganization?.toOrganizationDb() } +
                            objects.mapNotNull { it.extendedMol?.extendedOrganization?.toOrganizationDb() } +
                            objects.mapNotNull { it.extendedExploiting?.extendedOrganization?.toOrganizationDb() }
                    ).distinctBy { it.id }
        )

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
        departmentDao.insertAll(objects.mapNotNull { it.extendedDepartment?.toDepartmentDb() })
        providerDao.insertAll(objects.mapNotNull { it.extendedProvider?.toProviderDb() })
        statusesDao.insertAll(objects.mapNotNull { it.extendedAccountingObjectStatus?.toStatusDb() })
        branchesDao.insertAll(objects.mapNotNull { it.extendedBranch?.toBranchesDb() })
        accountingObjectsDao.insertAll(objects.map { it.toAccountingObjectDb() })
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

    private suspend fun regionsDbSaver(objects: List<RegionDtoV2>) {
        organizationsDao.insertAll(objects.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
        regionDao.insertAll(objects.map { it.toRegionDb() })
    }

    private suspend fun producersDbSaver(objects: List<ProducerDtoV2>) {
        producerDao.insertAll(objects.map { it.toProducerDb() })
    }

    private suspend fun organizationsDbSaver(objects: List<OrganizationDtoV2>) {
        organizationsDao.insertAll(objects.map { it.toOrganizationDb() })
    }

    private suspend fun equipmentsDbSaver(objects: List<EquipmentTypeDtoV2>) {
        equipmentTypeDao.insertAll(objects.map { it.toEquipmentTypeDb() })
    }

    private suspend fun employeesDbSaver(objects: List<EmployeeDtoV2>) {
        organizationsDao.insertAll(objects.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
        employeeDao.insertAll(objects.map { it.toEmployeeDb() })
    }

    private suspend fun departmentsDbSaver(objects: List<DepartmentDtoV2>) {
        organizationsDao.insertAll(objects.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
        departmentDao.insertAll(objects.map { it.toDepartmentDb() })
    }

    private suspend fun counterpartiesDbSaver(objects: List<CounterpartyDtoV2>) {
        counterpartyDao.insertAll(objects.map { it.toCounterpartyDb() })
    }

    private suspend fun branchesDbSaver(objects: List<BranchDtoV2>) {
        organizationsDao.insertAll(objects.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
        objects.map { it.toBranchesDb() }.let { dbBranches ->
            branchesDao.insertAll(dbBranches)
        }
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
        organizationsDao.insertAll(
            objects.mapNotNull { it.extendedBusinessUnit?.toOrganizationDb() } +
                    objects.mapNotNull { it.extendedMol?.extendedOrganization?.toOrganizationDb() } +
                    objects.mapNotNull { it.extendedStructuralSubdivision?.extendedOrganization?.toOrganizationDb() }
                        .distinctBy { it.id }
        )

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
        departmentDao.insertAll(objects.mapNotNull { it.extendedStructuralSubdivision?.toDepartmentDb() })
        receptionItemCategoryDao.insertAll(objects.mapNotNull { it.extendedReceptionItemCategory?.toReceptionItemCategoryDb() })
        orderDao.insertAll(objects.mapNotNull { it.extendedOrder?.toOrderDb() })
        reserveDao.insertAll(objects.map { it.toReserveDb() })
    }

    private suspend fun ordersDbSaver(objects: List<OrderDtoV2>) {
        orderDao.insertAll(objects.map { it.toOrderDb() })
    }

    private suspend fun receptionItemCategoryDbSaver(objects: List<ReceptionItemCategoryDtoV2>) {
        receptionItemCategoryDao.insertAll(objects.map { it.toReceptionItemCategoryDb() })
    }

    private suspend fun inventoryDbSaver(objects: List<InventoryDtoV2>) {
        organizationsDao.insertAll(
            objects.mapNotNull { it.extendedMol?.extendedOrganization?.toOrganizationDb() }
                .distinctBy { it.id }
        )

        employeeDao.insertAll(
            objects.mapNotNull { it.extendedMol?.toEmployeeDb() }.distinctBy { it.id }
        )

        locationDao.insertAll(objects.mapNotNull { it.extendedLocation?.toLocationDb() })
        inventoryDao.insertAll(objects.map { it.toInventoryDb() })
    }

    private suspend fun documentDbSaver(objects: List<ActionDtoV2>) {
        organizationsDao.insertAll(
            objects.mapNotNull { it.extendedMol?.extendedOrganization?.toOrganizationDb() }
                .distinctBy { it.id }
        )

        employeeDao.insertAll(
            objects.mapNotNull { it.extendedMol?.toEmployeeDb() }.distinctBy { it.id }
        )
        documentDao.insertAll(objects.map { it.toDocumentDb() })

        actionBaseDao.insertAll(
            objects.mapNotNull { it.extendedActionBase?.toActionBaseDb() }
        )
    }

    private suspend fun actionRecordDbSaver(objects: List<ActionRecordDtoV2>) {
        actionRecordDao.insertAll(objects.map { it.toActionRecordDb() })
    }

    private suspend fun actionRemainsRecordDbSaver(objects: List<ActionRemainsRecordDtoV2>) {
        actionRemainsRecordDao.insertAll(objects.map { it.toActionRemainsRecordDb() })
    }

    private suspend fun inventoryRecordDbSaver(objects: List<InventoryRecordDtoV2>) {
        inventoryRecordDao.insertAll(objects.map { it.toInventoryRecordDb() })
    }

    private suspend fun actionBasesDbSaver(objects: List<ActionBaseDtoV2>) {
        actionBaseDao.insertAll(objects.map { it.toActionBaseDb() })
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