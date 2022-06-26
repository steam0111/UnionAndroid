package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.DocumentDb
import com.example.union_sync_impl.entity.FullDocument
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {

    @RawQuery(observedEntities = [FullDocument::class])
    fun getAll(query: SupportSQLiteQuery): Flow<List<FullDocument>>

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
                "exploitingEmployees.nfc AS exploiting_nfc " +
                "" +
                "FROM documents " +
                "LEFT JOIN organizations ON documents.organizationId = organizations.id " +
                "LEFT JOIN employees molEmployees ON documents.molId = molEmployees.id " +
                "LEFT JOIN employees exploitingEmployees ON documents.exploitingId = exploitingEmployees.id " +
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
                "exploitingEmployees.nfc AS exploiting_nfc " +
                "" +
                "FROM documents " +
                "LEFT JOIN organizations ON documents.organizationId = organizations.id " +
                "LEFT JOIN employees molEmployees ON documents.molId = molEmployees.id " +
                "LEFT JOIN employees exploitingEmployees ON documents.exploitingId = exploitingEmployees.id " +
                "WHERE documents.id = :id LIMIT 1 "
    )
    suspend fun getDocumentById(id: Long): FullDocument

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(documentDb: DocumentDb): Long

    @Update
    suspend fun update(documentDb: DocumentDb)
}