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
    fun getAllSimpled(query: SupportSQLiteQuery): List<DocumentDb>

    @RawQuery
    fun getCount(query: SupportSQLiteQuery): Long

    @Query(
        "SELECT documents.*," +
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
                "exploitingEmployees.nfc AS exploiting_nfc " +
                "" +
                "FROM documents " +
                "LEFT JOIN employees molEmployees ON documents.molId = molEmployees.id " +
                "LEFT JOIN employees exploitingEmployees ON documents.exploitingId = exploitingEmployees.id " +
                "WHERE documents.documentType = :type"
    )
    fun getDocumentsByType(type: String): Flow<List<FullDocument>>

    @Query(
        "SELECT documents.*," +
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
                "exploitingEmployees.nfc AS exploiting_nfc " +
                "" +
                "FROM documents " +
                "LEFT JOIN employees molEmployees ON documents.molId = molEmployees.id " +
                "LEFT JOIN employees exploitingEmployees ON documents.exploitingId = exploitingEmployees.id " +
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

    @Query("SELECT code FROM documents WHERE code LIKE :number AND documentType is :documentType")
    suspend fun getDocumentsCodes(number: String, documentType: String?): List<String>
}