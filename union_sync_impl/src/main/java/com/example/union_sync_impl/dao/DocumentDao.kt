package com.example.union_sync_impl.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.DocumentDb
import com.example.union_sync_impl.entity.FullDocument
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {

    @RawQuery(observedEntities = [FullDocument::class])
    fun getAll(query: SupportSQLiteQuery): Flow<List<FullDocument>>

    @RawQuery
    fun getCount(query: SupportSQLiteQuery): Long

    @Query(
        "SELECT documents.*," +
                "" +
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
                "fromDepartments.id AS departmentFrom_id, " +
                "fromDepartments.organizationId AS departmentFrom_organizationId, " +
                "fromDepartments.name AS departmentFrom_name, " +
                "fromDepartments.catalogItemName AS departmentFrom_catalogItemName, " +
                "fromDepartments.code AS departmentFrom_code, " +
                "fromDepartments.updateDate AS departmentFrom_updateDate, " +
                "" +
                "toDepartments.id AS departmentTo_id, " +
                "toDepartments.organizationId AS departmentTo_organizationId, " +
                "toDepartments.name AS departmentTo_name, " +
                "toDepartments.catalogItemName AS departmentTo_catalogItemName, " +
                "toDepartments.code AS departmentTo_code, " +
                "toDepartments.updateDate AS departmentTo_updateDate, " +
                "" +
                "branches.id AS branches_id, " +
                "branches.organizationId AS branches_organizationId, " +
                "branches.name AS branches_name, " +
                "branches.catalogItemName AS branches_catalogItemName, " +
                "branches.code AS branches_code, " +
                "branches.updateDate AS branches_updateDate, " +
                "" +
                "action_base.id AS action_bases_id, " +
                "action_base.name AS action_bases_name, " +
                "action_base.updateDate AS action_bases_updateDate " +
                "" +
                "FROM documents " +
                "LEFT JOIN organizations ON documents.organizationId = organizations.id " +
                "LEFT JOIN employees molEmployees ON documents.molId = molEmployees.id " +
                "LEFT JOIN employees exploitingEmployees ON documents.exploitingId = exploitingEmployees.id " +
                "LEFT JOIN departments toDepartments ON documents.departmentToId = toDepartments.id " +
                "LEFT JOIN departments fromDepartments ON documents.departmentFromId = fromDepartments.id " +
                "LEFT JOIN branches ON documents.branchId = branches.id " +
                "LEFT JOIN action_base ON documents.actionBaseId = action_base.id " +
                "WHERE documents.documentType = :type"
    )
    fun getDocumentsByType(type: String): Flow<List<FullDocument>>

    @Query(
        "SELECT documents.*," +
                "" +
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
                "fromDepartments.id AS departmentFrom_id, " +
                "fromDepartments.organizationId AS departmentFrom_organizationId, " +
                "fromDepartments.name AS departmentFrom_name, " +
                "fromDepartments.catalogItemName AS departmentFrom_catalogItemName, " +
                "fromDepartments.code AS departmentFrom_code, " +
                "fromDepartments.updateDate AS departmentFrom_updateDate, " +
                "" +
                "toDepartments.id AS departmentTo_id, " +
                "toDepartments.organizationId AS departmentTo_organizationId, " +
                "toDepartments.name AS departmentTo_name, " +
                "toDepartments.catalogItemName AS departmentTo_catalogItemName, " +
                "toDepartments.code AS departmentTo_code, " +
                "toDepartments.updateDate AS departmentTo_updateDate, " +
                "" +
                "branches.id AS branches_id, " +
                "branches.organizationId AS branches_organizationId, " +
                "branches.name AS branches_name, " +
                "branches.catalogItemName AS branches_catalogItemName, " +
                "branches.code AS branches_code, " +
                "branches.updateDate AS branches_updateDate, " +
                "" +
                "action_base.id AS action_bases_id, " +
                "action_base.name AS action_bases_name, " +
                "action_base.updateDate AS action_bases_updateDate " +
                "" +
                "FROM documents " +
                "LEFT JOIN organizations ON documents.organizationId = organizations.id " +
                "LEFT JOIN employees molEmployees ON documents.molId = molEmployees.id " +
                "LEFT JOIN employees exploitingEmployees ON documents.exploitingId = exploitingEmployees.id " +
                "LEFT JOIN departments toDepartments ON documents.departmentToId = toDepartments.id " +
                "LEFT JOIN departments fromDepartments ON documents.departmentFromId = fromDepartments.id " +
                "LEFT JOIN branches ON documents.branchId = branches.id "   +
                "LEFT JOIN action_base ON documents.actionBaseId = action_base.id " +
                "WHERE documents.id = :id LIMIT 1 "
    )
    suspend fun getDocumentById(id: String): FullDocument

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(documentDb: DocumentDb)

    @Query("SELECT COUNT(*) FROM documents")
    suspend fun getDocumentCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(documents: List<DocumentDb>)

    @Update
    suspend fun update(documentDb: DocumentDb)

    @Query("DELETE FROM documents")
    suspend fun clearAll()
}