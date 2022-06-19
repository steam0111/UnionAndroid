package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.DepartmentDao
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.data.mapper.*
import com.example.union_sync_impl.entity.location.LocationPath
import com.example.union_sync_impl.entity.location.LocationTypeDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.openapitools.client.custom_api.AccountingObjectApi
import org.openapitools.client.models.CustomAccountingObjectDto
import org.openapitools.client.models.GetAllResponse

class AccountingObjectSyncApiImpl(
    private val api: AccountingObjectApi,
    private val accountingObjectsDao: AccountingObjectDao,
    private val organizationsDao: OrganizationDao,
    private val employeeDao: EmployeeDao,
    private val nomenclatureDao: NomenclatureDao,
    private val nomenclatureGroupDao: NomenclatureGroupDao,
    private val locationPathDao: LocationPathDao,
    private val locationDao: LocationDao,
    private val departmentDao: DepartmentDao
) : AccountingObjectSyncApi {

    override suspend fun getAccountingObjects(): Flow<List<AccountingObjectSyncEntity>> {
        return flow {
            emit(getDbData())
            syncAccountingObjects()
            emit(getDbData())
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }


    private suspend fun apiAccountingObjectsGet(): GetAllResponse<CustomAccountingObjectDto> {
        return api.apiAccountingObjectsGet()
    }

    private suspend fun syncAccountingObjects() {
        apiAccountingObjectsGet().list?.let { objects ->
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
            accountingObjectsDao.insertAll(objects.map { it.toAccountingObjectDb() })
        }
    }

    private suspend fun getDbData(): List<AccountingObjectSyncEntity> {
        return accountingObjectsDao.getAll()
            .map {
                val locationType: LocationTypeDb? =
                    locationDao.getLocationType(it.locationDb?.parentId)
                it.toSyncEntity(
                    it.locationDb?.toLocationSyncEntity(
                        locationType = locationType?.name.orEmpty(),
                        locationTypeId = locationType?.id.orEmpty()
                    )
                )
            }
    }
}