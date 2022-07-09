package com.example.union_sync_impl.sync

import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.AccountingObjectStatusDao
import com.example.union_sync_impl.dao.BranchesDao
import com.example.union_sync_impl.dao.CounterpartyDao
import com.example.union_sync_impl.dao.DepartmentDao
import com.example.union_sync_impl.dao.DocumentDao
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.EquipmentTypeDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.dao.OrderDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.dao.ProducerDao
import com.example.union_sync_impl.dao.ProviderDao
import com.example.union_sync_impl.dao.ReceptionItemCategoryDao
import com.example.union_sync_impl.dao.RegionDao
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.data.mapper.toAccountingObjectDb
import com.example.union_sync_impl.data.mapper.toAccountingObjectDtosV2
import com.example.union_sync_impl.data.mapper.toBranchesDb
import com.example.union_sync_impl.data.mapper.toCounterpartyDb
import com.example.union_sync_impl.data.mapper.toDepartmentDb
import com.example.union_sync_impl.data.mapper.toDocumentDb
import com.example.union_sync_impl.data.mapper.toEmployeeDb
import com.example.union_sync_impl.data.mapper.toEquipmentTypeDb
import com.example.union_sync_impl.data.mapper.toInventoryDb
import com.example.union_sync_impl.data.mapper.toLocationDb
import com.example.union_sync_impl.data.mapper.toLocationTypeDb
import com.example.union_sync_impl.data.mapper.toNomenclatureDb
import com.example.union_sync_impl.data.mapper.toNomenclatureGroupDb
import com.example.union_sync_impl.data.mapper.toOrderDb
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toProducerDb
import com.example.union_sync_impl.data.mapper.toProviderDb
import com.example.union_sync_impl.data.mapper.toReceptionItemCategoryDb
import com.example.union_sync_impl.data.mapper.toRegionDb
import com.example.union_sync_impl.data.mapper.toReserveDb
import com.example.union_sync_impl.data.mapper.toStatusDb
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectDtoV2
import org.openapitools.client.models.ActionDtoV2
import org.openapitools.client.models.BranchDtoV2
import org.openapitools.client.models.CounterpartyDtoV2
import org.openapitools.client.models.DepartmentDtoV2
import org.openapitools.client.models.EmployeeDtoV2
import org.openapitools.client.models.EquipmentTypeDtoV2
import org.openapitools.client.models.InventoryDtoV2
import org.openapitools.client.models.LocationDtoV2
import org.openapitools.client.models.LocationsTypeDtoV2
import org.openapitools.client.models.NomenclatureDtoV2
import org.openapitools.client.models.NomenclatureGroupDtoV2
import org.openapitools.client.models.OrderDtoV2
import org.openapitools.client.models.OrganizationDtoV2
import org.openapitools.client.models.ProducerDtoV2
import org.openapitools.client.models.ReceptionItemCategoryDtoV2
import org.openapitools.client.models.RegionDtoV2
import org.openapitools.client.models.RemainsDtoV2

class SyncRepository(
    private val syncControllerApi: SyncControllerApi,
    private val moshi: Moshi,
    private val accountingObjectsDao: AccountingObjectDao,
    private val organizationsDao: OrganizationDao,
    private val employeeDao: EmployeeDao,
    private val nomenclatureDao: NomenclatureDao,
    private val nomenclatureGroupDao: NomenclatureGroupDao,
    private val locationDao: LocationDao,
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
    private val documentDao: DocumentDao
) {
    fun getUploadSyncEntities(): List<UploadableSyncEntity> = listOf(
        AccountingObjectSyncEntity(
            syncControllerApi,
            moshi,
            ::accountingObjectDbSaver,
            getAccountingObjectDbCollector()
        )
    )

    fun getSyncEntities(): Map<String, SyncEntity<*>> = listOf(
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
            ::reservesDbSaver
        ),
        InventorySyncEntity(
            syncControllerApi,
            moshi,
            ::inventoryDbSaver
        ),
        DocumentSyncEntity(
            syncControllerApi,
            moshi,
            ::documentDbSaver
        )
    ).associateBy {
        it.id
    }

    private fun getAccountingObjectDbCollector(): Flow<List<AccountingObjectDtoV2>> {
        return flow {
            paginationEmitter(
                getData = { limit, offset ->
                    accountingObjectsDao.getAll(
                        sqlAccountingObjectQuery(
                            limit = limit,
                            offset = offset,
                            updateDate = System.currentTimeMillis()
                        )
                    )
                },
                localToNetworkMapper = { localObjects ->
                    localObjects.toAccountingObjectDtosV2()
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
        accountingObjectsDao.insertAll(objects.map { it.toAccountingObjectDb() })
    }

    private suspend fun locationsDbSaver(objects: List<LocationDtoV2>) {
        locationDao.insertAll(objects.map { it.toLocationDb() })
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
    }

}