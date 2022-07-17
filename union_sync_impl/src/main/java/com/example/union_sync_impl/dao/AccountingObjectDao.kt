package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.AccountingObjectUpdate
import com.example.union_sync_impl.entity.FullAccountingObject


@Dao
interface AccountingObjectDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<FullAccountingObject>

    @RawQuery
    fun getCount(query: SupportSQLiteQuery): Long

    @Query(
        "SELECT accounting_objects.*," +
                "organizations.id AS organizations_id, " +
                "organizations.catalogItemName AS organizations_catalogItemName, " +
                "organizations.name AS organizations_name, " +
                "organizations.actualAddress AS organizations_actualAddress, " +
                "organizations.legalAddress AS organizations_legalAddress, " +
                "" +
                "molEmployees.id AS mol_id, " +
                "molEmployees.catalogItemName AS mol_catalogItemName, " +
                "molEmployees.firstname AS mol_firstname, " +
                "molEmployees.lastname AS mol_lastname, " +
                "molEmployees.patronymic AS mol_patronymic, " +
                "molEmployees.organizationId AS mol_organizationId, " +
                "molEmployees.number AS mol_number, " +
                "molEmployees.nfc AS mol_nfc, " +
                "" +
                "exploitingEmployees.id AS exploiting_id, " +
                "exploitingEmployees.catalogItemName AS exploiting_catalogItemName, " +
                "exploitingEmployees.firstname AS exploiting_firstname, " +
                "exploitingEmployees.lastname AS exploiting_lastname, " +
                "exploitingEmployees.patronymic AS exploiting_patronymic, " +
                "exploitingEmployees.organizationId AS exploiting_organizationId, " +
                "exploitingEmployees.number AS exploiting_number, " +
                "exploitingEmployees.nfc AS exploiting_nfc, " +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId, " +
                "" +
                "locationTypes.id AS location_type_id, " +
                "locationTypes.catalogItemName AS location_type_catalogItemName, " +
                "locationTypes.name AS location_type_name, " +
                "locationTypes.parentId AS location_type_parentId, " +
                "" +
                "producer.id AS producer_id, " +
                "producer.catalogItemName AS producer_catalogItemName, " +
                "producer.name AS producer_name, " +
                "producer.code AS producer_code, " +
                "" +
                "equipment_types.id AS equipment_type_id, " +
                "equipment_types.catalogItemName AS equipment_type_catalogItemName, " +
                "equipment_types.name AS equipment_type_name, " +
                "equipment_types.code AS equipment_type_code, " +
                "" +
                "providers.id AS provider_id, " +
                "providers.catalogItemName AS provider_catalogItemName, " +
                "providers.name AS provider_name, " +
                "" +
                "departments.id AS department_id, " +
                "departments.catalogItemName AS department_catalogItemName, " +
                "departments.name AS department_name, " +
                "departments.code AS department_code, " +
                "" +
                "branches.id AS branches_id, " +
                "branches.catalogItemName AS branches_catalogItemName, " +
                "branches.name AS branches_name, " +
                "branches.organizationId AS branches_organizationId, " +
                "branches.code AS branches_code " +
                "" +
                "FROM accounting_objects " +
                "LEFT JOIN departments ON accounting_objects.departmentId = departments.id " +
                "LEFT JOIN providers ON accounting_objects.producerId = providers.id " +
                "LEFT JOIN equipment_types ON accounting_objects.equipmentTypeId = equipment_types.id " +
                "LEFT JOIN producer ON accounting_objects.producerId = producer.id " +
                "LEFT JOIN organizations ON accounting_objects.organizationId = organizations.id " +
                "LEFT JOIN locationTypes ON accounting_objects.locationTypeId = locationTypes.id " +
                "LEFT JOIN location ON accounting_objects.locationId = location.id " +
                "LEFT JOIN employees molEmployees ON accounting_objects.molId = molEmployees.id " +
                "LEFT JOIN employees exploitingEmployees ON accounting_objects.exploitingId = exploitingEmployees.id " +
                "LEFT JOIN branches ON accounting_objects.branchId = branches.id " +
                "WHERE accounting_objects.id = :id LIMIT 1"
    )
    suspend fun getById(id: String): FullAccountingObject

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objects: List<AccountingObjectDb>)

    @Update(entity = AccountingObjectDb::class)
    suspend fun update(accountingObjectUpdates: List<AccountingObjectUpdate>)
}