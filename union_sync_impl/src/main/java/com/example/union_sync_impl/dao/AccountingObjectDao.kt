package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.AccountingObjectLabelTypeUpdate
import com.example.union_sync_impl.entity.AccountingObjectMarkedUpdate
import com.example.union_sync_impl.entity.AccountingObjectScanningUpdate
import com.example.union_sync_impl.entity.AccountingObjectUpdate
import com.example.union_sync_impl.entity.AccountingObjectWriteOffUpdate
import com.example.union_sync_impl.entity.FullAccountingObject
import com.example.union_sync_impl.entity.PropertyCountEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AccountingObjectDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<FullAccountingObject>

    @RawQuery
    fun getAllSimpled(query: SupportSQLiteQuery): List<AccountingObjectDb>

    @RawQuery
    fun getCount(query: SupportSQLiteQuery): Long

    @Query(
        "SELECT accounting_objects.*," +
                "" +
                "structural.id AS structural_id, " +
                "structural.catalogItemName AS structural_catalogItemName, " +
                "structural.name AS structural_name, " +
                "structural.parentId AS structural_parentId, " +
                "" +
                "molEmployees.id AS mol_id, " +
                "molEmployees.catalogItemName AS mol_catalogItemName, " +
                "molEmployees.firstname AS mol_firstname, " +
                "molEmployees.lastname AS mol_lastname, " +
                "molEmployees.patronymic AS mol_patronymic, " +
                "molEmployees.number AS mol_number, " +
                "molEmployees.nfc AS mol_nfc, " +
                "" +
                "exploitingEmployees.id AS exploiting_id, " +
                "exploitingEmployees.catalogItemName AS exploiting_catalogItemName, " +
                "exploitingEmployees.firstname AS exploiting_firstname, " +
                "exploitingEmployees.lastname AS exploiting_lastname, " +
                "exploitingEmployees.patronymic AS exploiting_patronymic, " +
                "exploitingEmployees.number AS exploiting_number, " +
                "exploitingEmployees.nfc AS exploiting_nfc, " +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId, " +
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
                "providers.name AS provider_name " +
                "" +
                "FROM accounting_objects " +
                "LEFT JOIN providers ON accounting_objects.producerId = providers.id " +
                "LEFT JOIN equipment_types ON accounting_objects.equipmentTypeId = equipment_types.id " +
                "LEFT JOIN producer ON accounting_objects.producerId = producer.id " +
                "LEFT JOIN location ON accounting_objects.locationId = location.id " +
                "LEFT JOIN employees molEmployees ON accounting_objects.molId = molEmployees.id " +
                "LEFT JOIN structural ON accounting_objects.structuralId = structural.id " +
                "LEFT JOIN employees exploitingEmployees ON accounting_objects.exploitingId = exploitingEmployees.id " +
                "WHERE accounting_objects.id = :id LIMIT 1"
    )
    suspend fun getById(id: String): FullAccountingObject

    @RawQuery
    suspend fun getById(query: SupportSQLiteQuery): FullAccountingObject

    @Query(
        "SELECT accounting_objects.*," +
                "" +
                "structural.id AS structural_id, " +
                "structural.catalogItemName AS structural_catalogItemName, " +
                "structural.name AS structural_name, " +
                "structural.parentId AS structural_parentId, " +
                "" +
                "molEmployees.id AS mol_id, " +
                "molEmployees.catalogItemName AS mol_catalogItemName, " +
                "molEmployees.firstname AS mol_firstname, " +
                "molEmployees.lastname AS mol_lastname, " +
                "molEmployees.patronymic AS mol_patronymic, " +
                "molEmployees.number AS mol_number, " +
                "molEmployees.nfc AS mol_nfc, " +
                "" +
                "exploitingEmployees.id AS exploiting_id, " +
                "exploitingEmployees.catalogItemName AS exploiting_catalogItemName, " +
                "exploitingEmployees.firstname AS exploiting_firstname, " +
                "exploitingEmployees.lastname AS exploiting_lastname, " +
                "exploitingEmployees.patronymic AS exploiting_patronymic, " +
                "exploitingEmployees.number AS exploiting_number, " +
                "exploitingEmployees.nfc AS exploiting_nfc, " +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId, " +
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
                "label_type.id AS label_type_id, " +
                "label_type.name AS label_type_name," +
                "label_type.description AS label_type_description," +
                "label_type.code as label_type_code," +
                "label_type.catalogItemName AS label_type_catalogItemName " +
                "" +
                "FROM accounting_objects " +
                "LEFT JOIN providers ON accounting_objects.producerId = providers.id " +
                "LEFT JOIN equipment_types ON accounting_objects.equipmentTypeId = equipment_types.id " +
                "LEFT JOIN producer ON accounting_objects.producerId = producer.id " +
                "LEFT JOIN location ON accounting_objects.locationId = location.id " +
                "LEFT JOIN employees molEmployees ON accounting_objects.molId = molEmployees.id " +
                "LEFT JOIN structural ON accounting_objects.structuralId = structural.id " +
                "LEFT JOIN label_type ON accounting_objects.labelTypeId = label_type.id " +
                "LEFT JOIN employees exploitingEmployees ON accounting_objects.exploitingId = exploitingEmployees.id " +
                "WHERE accounting_objects.id = :id LIMIT 1"
    )
    fun getByIdFlow(id: String): Flow<FullAccountingObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objects: List<AccountingObjectDb>)

    @Update(entity = AccountingObjectDb::class)
    suspend fun update(accountingObjectUpdates: List<AccountingObjectUpdate>)

    @Update(entity = AccountingObjectDb::class)
    suspend fun update(accountingObjectUpdate: AccountingObjectWriteOffUpdate)

    @Update(entity = AccountingObjectDb::class)
    suspend fun updateWriteOffAccountingObjects(accountingObjectUpdates: List<AccountingObjectWriteOffUpdate>)

    @Update(entity = AccountingObjectDb::class)
    suspend fun update(accountingObjectUpdate: AccountingObjectScanningUpdate)

    @Update(entity = AccountingObjectDb::class)
    suspend fun update(accountingObjectUpdate: AccountingObjectLabelTypeUpdate)

    @Update(entity = AccountingObjectDb::class)
    suspend fun update(accountingObjectUpdate: AccountingObjectMarkedUpdate)

    @Query("SELECT COUNT(*) as positionsCount, SUM(count) as allCount FROM accounting_objects")
    suspend fun getPropertyCount(): PropertyCountEntity
}