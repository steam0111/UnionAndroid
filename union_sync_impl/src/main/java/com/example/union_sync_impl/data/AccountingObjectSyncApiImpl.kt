package com.example.union_sync_impl.data

import androidx.sqlite.db.SimpleSQLiteQuery
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
import com.example.union_sync_impl.data.mapper.toAccountingObjectDb
import com.example.union_sync_impl.data.mapper.toDepartmentDb
import com.example.union_sync_impl.data.mapper.toEmployeeDb
import com.example.union_sync_impl.data.mapper.toLocationDb
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toNomenclatureDb
import com.example.union_sync_impl.data.mapper.toNomenclatureGroupDb
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
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

    override suspend fun getAccountingObjects(
        organizationId: String?,
        molId: String?
    ): Flow<List<AccountingObjectSyncEntity>> {
        return flow {
            emit(getDbData(organizationId, molId))
            syncAccountingObjects()
            emit(getDbData(organizationId, molId))
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

    private suspend fun getDbData(
        organizationId: String? = null,
        molId: String? = null
    ): List<AccountingObjectSyncEntity> {
        val filters = mutableListOf<String>()

        if (organizationId != null) {
            filters.add("accounting_objects.organizationId = \'$organizationId\'")
        }
        if (molId != null) {
            filters.add("accounting_objects.molId = \'$molId\'")
        }

        val filterExpression = if (filters.isNotEmpty()) {
            "WHERE ${filters.joinToString(separator = " AND ")}"
        } else {
            ""
        }

        val getAccountingObjects = SimpleSQLiteQuery(
            "SELECT accounting_objects.*," +
                    "" +
                    "location.id AS locations_id, " +
                    "location.catalogItemName AS locations_catalogItemName, " +
                    "location.name AS locations_name, " +
                    "location.parentId AS locations_parentId " +
                    "" +
                    "FROM accounting_objects " +
                    "LEFT JOIN location ON accounting_objects.locationId = location.id " +
                    filterExpression
        )

        return accountingObjectsDao.getAll(getAccountingObjects)
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