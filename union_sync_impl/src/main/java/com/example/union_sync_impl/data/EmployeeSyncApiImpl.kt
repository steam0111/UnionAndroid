package com.example.union_sync_impl.data

import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.EnumType
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.StructuralDao
import com.example.union_sync_impl.dao.sqlEmployeeQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toStructuralSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity

class EmployeeSyncApiImpl(
    private val employeeDao: EmployeeDao,
    private val structuralSyncApi: StructuralSyncApi,
    private val enumSyncApi: EnumsSyncApi
) : EmployeeSyncApi {
    override suspend fun getEmployees(
        textQuery: String?,
        structuralId: String?,
        offset: Long?,
        limit: Long?
    ): List<EmployeeSyncEntity> {
        val employeeDb = employeeDao.getAll(
            sqlEmployeeQuery(
                structuralId,
                textQuery,
                limit = limit,
                offset = offset
            )
        )
        return employeeDb.map { it.toSyncEntity() }
    }

    override suspend fun getEmployeesCount(textQuery: String?, structuralId: String?): Long {
        return employeeDao.getCount(
            sqlEmployeeQuery(
                structuralId,
                textQuery,
                isFilterCount = true
            )
        )
    }

    override suspend fun getEmployeeDetail(id: String): EmployeeDetailSyncEntity {
        val fullEmployee = employeeDao.getFullById(id)
        val structurals =
            structuralSyncApi.getStructuralFullPath(
                fullEmployee.structural?.id,
                mutableListOf()
            ).orEmpty()
        val balanceUnitIndex = structurals.indexOfLast { it.balanceUnit }.takeIf { it >= 0 } ?: 0
        val balanceUnits = structurals.subList(0, balanceUnitIndex)

        val employeeStatus = enumSyncApi.getByCompoundId(
            enumType = EnumType.EMPLOYEE_STATUS,
            id = fullEmployee.employeeDb.statusId
        )

        return fullEmployee.toDetailSyncEntity(
            balanceUnits = balanceUnits,
            structurals = structurals,
            employeeStatusSyncEntity = employeeStatus
        )
    }
}