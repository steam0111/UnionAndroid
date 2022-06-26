package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AllSyncApi
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.AccountingObjectStatusDao
import com.example.union_sync_impl.dao.BranchesDao
import com.example.union_sync_impl.dao.CounterpartyDao
import com.example.union_sync_impl.dao.DepartmentDao
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.EquipmentTypeDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.dao.ProducerDao
import com.example.union_sync_impl.dao.ProviderDao
import com.example.union_sync_impl.dao.RegionDao
import com.example.union_sync_impl.data.mapper.toAccountingObjectDb
import com.example.union_sync_impl.data.mapper.toBranchesDb
import com.example.union_sync_impl.data.mapper.toCounterpartyDb
import com.example.union_sync_impl.data.mapper.toDepartmentDb
import com.example.union_sync_impl.data.mapper.toEmployeeDb
import com.example.union_sync_impl.data.mapper.toEquipmentTypeDb
import com.example.union_sync_impl.data.mapper.toLocationDb
import com.example.union_sync_impl.data.mapper.toLocationTypeDb
import com.example.union_sync_impl.data.mapper.toNomenclatureDb
import com.example.union_sync_impl.data.mapper.toNomenclatureGroupDb
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toProducerDb
import com.example.union_sync_impl.data.mapper.toProviderDb
import com.example.union_sync_impl.data.mapper.toRegionDb
import com.example.union_sync_impl.data.mapper.toStatusDb
import com.example.union_sync_impl.entity.NetworkSyncDb
import org.openapitools.client.custom_api.AccountingObjectApi
import org.openapitools.client.custom_api.BranchesApi
import org.openapitools.client.custom_api.CounterpartyApi
import org.openapitools.client.custom_api.DepartmentApi
import org.openapitools.client.custom_api.EmployeeApi
import org.openapitools.client.custom_api.EquipmentTypeApi
import org.openapitools.client.custom_api.LocationApi
import org.openapitools.client.custom_api.NomenclaturesApi
import org.openapitools.client.custom_api.OrganizationApi
import org.openapitools.client.custom_api.ProducerApi
import org.openapitools.client.custom_api.RegionApi
import org.openapitools.client.models.CustomLocationDto
import org.openapitools.client.models.CustomLocationsTypeDto

class AllSyncImpl(
    private val api: AccountingObjectApi,
    private val accountingObjectsDao: AccountingObjectDao,
    private val organizationsDao: OrganizationDao,
    private val employeeDao: EmployeeDao,
    private val nomenclatureDao: NomenclatureDao,
    private val nomenclatureGroupDao: NomenclatureGroupDao,
    private val locationPathDao: LocationPathDao,
    private val locationDao: LocationDao,
    private val departmentDao: DepartmentDao,
    private val providerDao: ProviderDao,
    private val nomenclatureGroupApi: NomenclaturesApi,
    private val branchesApi: BranchesApi,
    private val branchesDao: BranchesDao,
    private val organizationDao: OrganizationDao,
    private val counterpartyApi: CounterpartyApi,
    private val counterpartyDao: CounterpartyDao,
    private val departmentApi: DepartmentApi,
    private val employeeApi: EmployeeApi,
    private val equipmentTypeDao: EquipmentTypeDao,
    private val equipmentTypeApi: EquipmentTypeApi,
    private val locationApi: LocationApi,
    private val organizationApi: OrganizationApi,
    private val producerApi: ProducerApi,
    private val producerDao: ProducerDao,
    private val regionApi: RegionApi,
    private val regionDao: RegionDao,
    private val statusesDao: AccountingObjectStatusDao,
    private val networkSyncDao: NetworkSyncDao
) : AllSyncApi {

    override suspend fun isSynced(): Boolean {
        return networkSyncDao.getNetworkSync()?.isAllSynced ?: false
    }

    override suspend fun syncAll() {
        syncProducers()
        syncCounterparty()
        syncNomenclatureGroup()

        syncNomenclature()

        syncOrganization()
        syncEmployee()
        syncDepartment()
        syncBranches()
        syncRegions()

        syncEquipment()
        syncLocations()

        syncAccountingObjects()

        networkSyncDao.insert(
            NetworkSyncDb(
                isAllSynced = true
            )
        )
    }

    private suspend fun syncRegions() {
        val regionNetwork = regionApi.apiCatalogRegionGet().list.orEmpty()
        regionNetwork.mapNotNull { it?.extendedOrganization?.toOrganizationDb() }
            .let { dbOrganization -> organizationDao.insertAll(dbOrganization) }

        regionNetwork.mapNotNull { it?.toRegionDb() }.let { dbRegion ->
            regionDao.insertAll(dbRegion)
        }
    }

    private suspend fun syncProducers() {
        val producerNetwork = producerApi.apiCatalogsProducerGet().list.orEmpty()
        producerNetwork.map { it.toProducerDb() }.let { dbProducer ->
            producerDao.insertAll(dbProducer)
        }
    }

    private suspend fun syncOrganization() {
        val organizationNetwork = organizationApi.apiCatalogsOrganizationGet().list
        organizationNetwork?.map { it.toOrganizationDb() }?.let { dbOrganizations ->
            organizationDao.insertAll(dbOrganizations)
        }
    }

    private suspend fun syncLocations() {
        kotlin.runCatching { //TODO посже необходимо убрат, сейчас без этого в прилу не попасть
            val networkLocationTypes: List<CustomLocationsTypeDto> =
                requireNotNull(locationApi.apiLocationsTypesGet().list)
            locationDao.insertAllLocationTypes(networkLocationTypes.map { it.toLocationTypeDb() })
        }

        kotlin.runCatching { //TODO посже необходимо убрат, сейчас без этого в прилу не попасть
            val networkLocations: List<CustomLocationDto> =
                requireNotNull(locationApi.apiLocationsGet().list)
            locationDao.insertAll(networkLocations.map { it.toLocationDb() })
        }
    }

    private suspend fun syncEquipment() {
        val networkData = equipmentTypeApi.apiCatalogEquipmentTypesGet().list.orEmpty()
        networkData.map { it.toEquipmentTypeDb() }.let { dbEquipmentType ->
            equipmentTypeDao.insertAll(dbEquipmentType)
        }
    }

    private suspend fun syncEmployee() {
        val employeeNetwork = employeeApi.apiCatalogsEmployeesGet().list ?: emptyList()
        organizationDao.insertAll(employeeNetwork.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
        employeeDao.insertAll(employeeNetwork.map { it.toEmployeeDb() })
    }

    private suspend fun syncDepartment() {
        val departmentNetwork =
            departmentApi.apiCatalogsDepartmentGet().list ?: emptyList()
        organizationDao.insertAll(departmentNetwork.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
        departmentDao.insertAll(departmentNetwork.map { it.toDepartmentDb() })
    }

    private suspend fun syncCounterparty() {
        val counterpartyNetwork = counterpartyApi.apiCatalogsCounterpartyGet().list.orEmpty()
        counterpartyNetwork.map { it.toCounterpartyDb() }.let { dbCounterparty ->
            counterpartyDao.insertAll(dbCounterparty)
        }
    }

    private suspend fun syncBranches() {
        val branchesNetwork = branchesApi.apiCatalogsBranchesGet().list.orEmpty()
        organizationDao.insertAll(branchesNetwork.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
        branchesNetwork.map { it.toBranchesDb() }.let { dbBranches ->
            branchesDao.insertAll(dbBranches)
        }
    }

    private suspend fun syncNomenclatureGroup() {
        kotlin.runCatching { //TODO посже необходимо убрат, сейчас без этого в прилу не попасть
            val nomenclaturesGroupNetwork =
                nomenclatureGroupApi.apiCatalogsNomenclatureGroupGet().list ?: emptyList()

            nomenclatureGroupDao.insertAll(nomenclaturesGroupNetwork.map { it.toNomenclatureGroupDb() })
        }
    }

    private suspend fun syncNomenclature() {
        kotlin.runCatching { //TODO посже необходимо убрат, сейчас без этого в прилу не попасть
            val nomenclatureNetwork =
                nomenclatureGroupApi.apiCatalogsNomenclatureGet().list ?: emptyList()

            nomenclatureGroupDao.insertAll(
                nomenclatureNetwork.mapNotNull {
                    it.extendedNomenclatureGroup?.toNomenclatureGroupDb()
                }
            )
            nomenclatureDao.insertAll(
                nomenclatureNetwork.map {
                    it.toNomenclatureDb()
                }
            )
        }
    }

    private suspend fun syncAccountingObjects() {
        api.apiAccountingObjectsGet().list?.let { objects ->
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
    }
}